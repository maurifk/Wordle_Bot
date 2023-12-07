#include <iostream>
#include <fstream>
#include <unordered_set>
#include <string>
#include <vector>
#include <queue>
#include <chrono>
#include <filesystem>

void cargarPalabras(std::unordered_set<int>& conjunto, std::vector<std::string>& lista, const std::string& nombreArchivo, int numeroPals) {
    std::string palabra;
    std::ifstream archivo;
    archivo.open(nombreArchivo, std::ios::in);

    if (archivo.is_open()) {
        for (int i = 0; i < numeroPals; ++i) {
            if (std::getline(archivo, palabra)) {
                conjunto.insert(i);
                lista[i] = palabra;
            } else {
                std::cout << "Error: No se pudieron leer todas las palabras del archivo." << std::endl;
                break;
            }
        }
        archivo.close();
    } else {
        std::cout << "Error al abrir el archivo: " << nombreArchivo << std::endl;
    }
}

bool palabraValida (std::string palabra, const std::string& objetivo, const std::string& colores){
    for (int i = 0; i < 5; ++i){
        if ((colores[i] == '0' || colores[i] == '3') && palabra[i] == objetivo[i]){
            return false;
        }
        if (colores[i] == '5'){
            if (objetivo[i] != palabra[i]){
                return false;
            } else {
                palabra[i] = '[';
            }
        }
    }

    for (int i = 0; i < 5; ++i){
        if (colores[i] == '3'){
            for(int j = 0; j < 5; ++j){
                if(palabra[j] == objetivo[i]){
                    palabra[j] = '[';
                    break;
                }
            }
            return false;
        }
    }

    for (int i = 0; i < 5; ++i){
        if (colores[i] == '0'){
            for (int j = 0; j < 5; ++j){
                if (palabra[j] == objetivo[i]){
                    return false;
                }
            }
        }
    }


    return true;
}

int puntajePal (std::string pal, const std::string& objetivo){
    const int puntajeVerde = 5;
    const int puntajeAmarillo = 3;
    int puntaje = 0;
    std::string colores = "GGGGG";

    for (int i = 0; i < 5; ++i){
        if (pal[i] == objetivo[i]){
            puntaje += puntajeVerde;
            pal[i] = '[';
            colores[i] = 'V';
        }
    }

    for (int i = 0; i < 5; ++i){
        if (colores[i] != 'V'){
            for (int j = 0; j < 5; ++j){
                if (pal[j] == objetivo[i]){
                    colores[i] = 'A';
                    pal[j] = '[';
                    puntaje += puntajeAmarillo;
                }
            }
        }
    }

    return puntaje;
}

void filtar (std::unordered_set<int>& palabras, const std::vector<std::string>& lista, const std::string& pal, const std::string& colores){
    std::vector<std::unordered_set<int>::iterator> palsAEliminar;

    for (auto it = palabras.begin(); it != palabras.end(); ++it)
        if (!palabraValida(lista[*it], pal, colores))
            palsAEliminar.push_back(it);

    for (auto it = palsAEliminar.begin(); it != palsAEliminar.end(); ++it)
        palabras.erase(*it);
}

bool generarMatriz(std::vector<std::vector<char>>& mat, const std::vector<std::string>& lista, const std::string& idiom){
    std::string nombreArchivo = idiom + "_matriz.txt";
    if (std::filesystem::exists(nombreArchivo)) {
        std::cout << "Salteo la generacion, ya esta creado el archivo." << std::endl;
        return false;
    }

    for (std::size_t i = 0; i < mat.size(); ++i)
        for (std::size_t j = 0; j < mat[0].size(); ++j)
            mat[i][j] = (char) puntajePal(lista[i], lista[j]);
    return true;
}

void guardarMatriz(const std::vector<std::vector<char>>& mat, const std::string& idiom){
    std::ofstream archivo;
    std::string nombreArchivo = idiom + "_matriz.txt";
    archivo.open(nombreArchivo);
    for (std::size_t i = 0; i < mat.size(); ++i){
        for (std::size_t j = 0; j < mat[0].size(); ++j){
            archivo << (int)mat[i][j] << " ";
        }
        archivo << std::endl;
    }
    archivo.close();
}

void cargarMatriz(std::vector<std::vector<char>>& mat, const std::string& idiom){
    std::ifstream archivo;
    std::string nombreArchivo = idiom + "_matriz.txt";
    archivo.open(nombreArchivo);
    for (std::size_t i = 0; i < mat.size(); ++i){
        for (std::size_t j = 0; j < mat[0].size(); ++j){
            int num;
            archivo >> num;
            mat[i][j] = (char)num;
        }
    }
}

std::vector<std::string> topPalabras (const std::unordered_set<int>& palabras, const std::vector<std::string>& lista, const std::vector<std::vector<char>>& matriz){
    std::priority_queue <std::pair<int, std::string>, std::vector<std::pair<int, std::string>>, std::greater<>> pq;
    for (auto iti = palabras.begin(); iti != palabras.end(); ++iti){
        int puntaje = 0;
        for (auto it = palabras.begin(); it != palabras.end(); ++it){
            puntaje += (int)matriz[*iti][*it];
        }

        if (pq.size() < 5){
            pq.push({ puntaje, lista[*iti] });
        } else {
            if (pq.top().first < puntaje){
                pq.pop();
                pq.push({ puntaje, lista[*iti] });
            }
        }
    }
    std::vector<std::string> res;
    while (!pq.empty()){
        res.push_back(pq.top().second);
        pq.pop();
    }
    return res;
}

int main() {
    int cantPals;
    std::string nArchivo;

    std::string idioma;
    std::cout << "Elija el idioma: (esp / eng / engF)" << std::endl;
    std::cin >> idioma;

    if (idioma == "esp"){
        std::cout << "Se eligio español." << std::endl;
        cantPals = 10836;
        nArchivo = "palabras5letras.txt";
    } else if ( idioma == "eng"){
        std::cout << "Se eligio ingles." << std::endl;
        cantPals = 12972;
        nArchivo = "palabrasENG.txt";
    } else {
        std::cout << "Se eligio ingles filtrado." << std::endl;
        cantPals = 2315;
        nArchivo = "palabrasENGFil.txt";
    }

    std::vector<std::vector<char>> matriz(cantPals, std::vector<char>(cantPals, 0));

    std::unordered_set<int> palsValidas;
    std::vector<std::string> listaPals(cantPals, "");

    cargarPalabras(palsValidas, listaPals, nArchivo, cantPals);
    std::cout << "--- Generando matriz ---" << std::endl;
    auto start = std::chrono::high_resolution_clock::now();
    bool genero = generarMatriz(matriz, listaPals, idioma);
    auto stop = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::seconds>(stop - start);

    if (genero) {
        std::cout << "Demoró " << duration.count() << " segundos en generar la matriz" << std::endl;
        std::cout << "--- Guardando matriz ---" << std::endl;
        start = std::chrono::high_resolution_clock::now();
        guardarMatriz(matriz, idioma);
        stop = std::chrono::high_resolution_clock::now();
        duration = std::chrono::duration_cast<std::chrono::seconds>(stop - start);
        std::cout << "Demoró " << duration.count() << " segundos en guardar la matriz" << std::endl;
    } else {
        std::cout << "--- Cargando matriz ---" << std::endl;
        start = std::chrono::high_resolution_clock::now();
        cargarMatriz(matriz, idioma);
        stop = std::chrono::high_resolution_clock::now();
        duration = std::chrono::duration_cast<std::chrono::seconds>(stop - start);
        std::cout << "Demoró " << duration.count() << " segundos en cargar la matriz" << std::endl;
    }

    int jugadas = 1;
    while (true) {
        std::cout << "--- Calculando mejores palabras ---" << std::endl;
        std::vector<std::string> mejores = topPalabras(palsValidas, listaPals, matriz);
        std::cout << "Quedan " << palsValidas.size() << " palabras, y las mejores son:" << std::endl;
        int i = 1;
        for (auto it = mejores.begin(); it != mejores.end(); ++it){
            std::cout << i++ << ". " << *it << std::endl;
        }

        if (jugadas++ >= 4){
            std::cout << "Desea seguir? (s/n/r)" << std::endl;
            std::string seg;
            std::cin >> seg;
            seg[0] = (char)tolower(seg[0]);
            if (seg[0] == 'n')
                break;
            if (seg[0] == 'r'){
                std::cout << "--- Reiniciando el ayudante ---" << std::endl;
                jugadas = 1;
                for (int j = 0; j < cantPals; ++j)
                    palsValidas.insert(j);
                continue;
            }
        }

        std::cout << "Ingrese la palabra elegida:" << std::endl;
        std::string palJugada;
        std::cin >> palJugada;
        std::cout << "Ingrese los colores obtenidos (gris = 0, amarillo = 3, verde = 5)" << std::endl;
        std::string colores;
        std::cin >> colores;

        std::cout << "--- Filtrando palabras ---" << std::endl;
        filtar(palsValidas, listaPals, palJugada, colores);
    }

    return 0;
}
