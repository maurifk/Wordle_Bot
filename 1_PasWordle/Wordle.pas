program Wordle;
{$IFDEF FPC}
  {$MODE DELPHI}
{$ENDIF}
 
 uses
  Sysutils;
  
 type
  color = (gris = 0, amarillo = 3, verde = 5); 

  Letra = record 
         ltr  : char;
         clr : color;
          end;
       
  Palabra = array [1..5] of Letra;
  
  ListaPalabras = record
			pals : array [1..10836] of Palabra;
			tope : integer;
			      end;

  Matriz = array [1 .. 5418,1 .. 5418] of string;
{ 	Matriz2 = array [1 .. 5418,5419 .. 10836] of string;		     
  Matriz3 = array [5419..10836,1 .. 5418] of string;
  Matriz4 = array [5419 .. 10836,5419 .. 10836] of string; }
  MatrizPreGenerada = array [1 .. 10836,1 .. 10836] of string;

  
  PalProm = record
		pal : Palabra;
		prom : Real;
			end;
  
  ListaProms = array [1..10836] of PalProm;

  ListaBool = array [1 .. 10836] of boolean;
  AuxBools = array [1..5] of boolean;
  
  var i, cantpals : integer;
      Lista : ListaPalabras; 
      tfIn : TextFile;
      listprom : ListaProms;
      lb : ListaBool;
      s1, s2 : string;
  
   
 function compararLetras(letra1,letra2 : Letra):boolean;
  begin
   compararLetras := letra1.ltr = letra2.ltr
  end;

 function apareceLetraPalabra(letra1 : Letra; palabra1 : Palabra):boolean;
 var i : integer;
   begin
    i:= 1;
    while (i <= 5) and not (compararLetras(letra1, palabra1[i]))
     do i:= i+1;
    apareceLetraPalabra := i <= 5;
  end;

  procedure sacarLetraPalabra(letra1 : Letra; pos : integer; var palabra1 : Palabra);
  begin
	palabra1[pos].ltr := '['
  end;
  
  function posLetra(letra1 : Letra; palabra1 : Palabra):integer;
  var i : integer;
   begin
    i:= 1;
    while (i <= 5) and not (compararLetras(letra1, palabra1[i]))
     do i:= i+1;
    posLetra := i;
  end;

  procedure colorearLetras(palabra1: Palabra; var palabra2 : Palabra);
    var i : integer;
    begin
      for i := 1 to 5 do
		    palabra2[i].clr := gris;
		
      for i := 1 to 5 do
		    if palabra1[i].ltr = palabra2[i].ltr then
			    begin
			      palabra2[i].clr := verde;
			      sacarLetraPalabra(palabra2[i], i, palabra1)
			    end;
			
	    for i := 1 to 5 do
		    if apareceLetraPalabra(palabra2[i], palabra1) then
		      begin
			      palabra2[i].clr := amarillo;
			      sacarLetraPalabra(palabra2[i], posLetra(palabra2[i],palabra1), palabra1)
		      end;
    end;
  
  procedure imprimirPalabra (pal : Palabra);
  var i : integer;
  begin
    for i := 1 to 5 do
      Write (pal[i].ltr)
  end;
  
  function stringdeColores(palabra1, palabra2 : Palabra):string;
    var i : integer;
    begin
      stringdeColores := '00000';
      for i := 1 to 5 do
		    stringdeColores[i] := '0';
		
      for i := 1 to 5 do
		    if palabra1[i].ltr = palabra2[i].ltr then
			    begin
			      stringdeColores[i] := '5';
			      sacarLetraPalabra(palabra2[i], i, palabra1)
			    end;
			
	    for i := 1 to 5 do
		    if apareceLetraPalabra(palabra2[i], palabra1) then
		      begin
			      stringdeColores[i] := '3';
			      sacarLetraPalabra(palabra2[i], posLetra(palabra2[i],palabra1), palabra1)
		      end;
    end;

   function stringaPalabra (s : string):Palabra;
   begin
    for i := 1 to 5 do
		  stringaPalabra[i].ltr := s[i];
   end;

   procedure generarLista(var Lista : ListaPalabras; var tfIn : TextFile);
   var s : string;
   begin 
	  Lista.tope := 0;
	  AssignFile(tfIn, 'palabras5letras.txt');
	
	  try
      // Open the file for reading
      reset(tfIn);

      // Keep reading lines until the end of the file is reached
      while not eof(tfIn) do
      begin
        readln(tfIn, s); 
        Lista.tope := Lista.tope + 1;
        Lista.pals[Lista.tope] := stringaPalabra(s);
      end;

      // Done so close the file
      CloseFile(tfIn);
	  except
      on E: EInOutError do
      writeln('File handling error occurred. Details: ', E.Message);
    end;
  end;
  
  function palabrasIguales(palabra1, palabra2 : Palabra):boolean;
  var i : integer;
  begin
    i := 1;
    while (i < 6) and (palabra1[i].ltr = palabra2[i].ltr) do
      i := i + 1;
    palabrasIguales := i = 6;
  end;
  
  function coloresaString (palabra1 : Palabra):string;
  begin
    coloresaString := '00000';
    for i := 1 to 5 do
      coloresaString[i] := char(ord(palabra1[i].clr)+48)
  end;

  procedure generarMatriz1(var lista : ListaPalabras; var matrizcolores : Matriz);
  var i,j : integer;
  begin
    for i := 1 to 5418 do
      for j := 1 to 5418 do
        begin
          matrizcolores[i,j] := stringdeColores(lista.pals[j], lista.pals[i]);
        end;
  end;
  procedure generarMatriz2(var lista : ListaPalabras; var matrizcolores : Matriz);
  var i,j : integer;
  begin
    for i := 1 to 5418 do
      for j := 1 to 5418 do
        begin
          matrizcolores[i,j] := stringdeColores(lista.pals[j+5418], lista.pals[i]);
        end;
  end;
  procedure generarMatriz3(var lista : ListaPalabras; var matrizcolores : Matriz);
  var i,j : integer;
  begin
    for i := 1 to 5418 do
      for j := 1 to 5418 do
        begin
          matrizcolores[i,j] := stringdeColores(lista.pals[j], lista.pals[i+5418]);
        end;
  end;
  procedure generarMatriz4(var lista : ListaPalabras; var matrizcolores : Matriz);
  var i,j : integer;
  begin
    for i := 1 to 5418 do
      for j := 1 to 5418 do
        begin
          matrizcolores[i,j] := stringdeColores(lista.pals[j+5418], lista.pals[i+5418]);
        end;
  end;

  procedure guardartxtMatriz (var matrizcol : Matriz; var tfOut : TextFile);
  var i,j : integer;
  begin
  // Set the name of the file that will receive some more text
  AssignFile(tfOut, 'MatrizGenerada.txt');

  // Embed the file handling in a try/except block to handle errors gracefully
    try
    // Open the file for appending, write some more text to it and close it.
      append(tfOut);

      for i := 1 to 5418 do
        for j := 1 to 5418 do
          writeln(tfOut, matrizcol[i,j]);

      CloseFile(tfOut);

    except
      on E: EInOutError do
       writeln('File handling error occurred. Details: ', E.Message);
    end;
  end;

  procedure generarMatrizPG(var MatrizPG : MatrizPreGenerada; var tfIn : TextFile);
   var s : string; i,j : integer;
   begin 
	  Lista.tope := 0;
	  AssignFile(tfIn, 'MatrizGenerada.txt');
	
	  try
      // Open the file for reading
      reset(tfIn);

      // Keep reading lines until the end of the file is reached
      for i := 1 to 5418 do
        for j := 1 to 5418 do
          begin
            readln(tfIn, s); 
            MatrizPG[i,j] := s; 
          end;
      
      for i := 1 to 5418 do
        for j := 5419 to 10836 do
          begin
            readln(tfIn, s); 
            MatrizPG[i,j] := s; 
          end;
      
      for i := 5419 to 10836 do
        for j := 1 to 5418 do
          begin
            readln(tfIn, s); 
            MatrizPG[i,j] := s; 
          end;
      
      for i := 5419 to 10836 do
        for j := 5419 to 10836 do
          begin
            readln(tfIn, s); 
            MatrizPG[i,j] := s; 
          end;

      // Done so close the file
      CloseFile(tfIn);
	  except
      on E: EInOutError do
      writeln('File handling error occurred. Details: ', E.Message);
    end;
  end;

  procedure crearListaBool (var lb : ListaBool);
  var i : integer;
  begin
    for i := 1 to 10836 do
      lb[i] := true;
  end;

  function sumaString (s : string):integer;
  var i, contador : integer;
  begin
    contador := 0;
    for i := 1 to 5 do
      contador := contador + ord(s[i]) - 48;
    sumaString := contador;
  end;

  function palabraaString (p : Palabra):string;
  var i : integer; s : string;
  begin
    S := '';
    for i := 1 to 5 do
      s := s + p[i].ltr;
    palabraaString := s;
  end;

  procedure calcPromedios (lista : ListaPalabras; lb : ListaBool; cantpals : integer; var listprom : ListaProms);
  var  i,j : integer;
  begin
    for i := 1 to 10836 do
      begin
        if lb[i] then
        begin
          listprom[i].prom := 0;
          listprom[i].pal := lista.pals[i];
          for j := 1 to 10836 do
            if lb[j] then
              listprom[i].prom := listprom[i].prom + sumaString(stringdeColores(lista.pals[j], lista.pals[i]));
          listprom[i].prom := listprom[i].prom / cantpals;
        end;    
      end;
  end;

  function mejorPalabra(listap : ListaProms; lb : ListaBool):Palabra;
  var i, tempmeji : integer; tempmej : real;
  begin
    tempmeji := 0;
    tempmej := 0;
    for i := 1 to 10836 do
      if (listap[i].prom >= tempmej) and (lb[i]) then
        begin
          tempmej := listap[i].prom;
          tempmeji := i;
        end;
    mejorPalabra := listap[tempmeji].pal
  end;

  procedure imptopPalabras(listap : ListaProms; lb : ListaBool);
  var i, tempmeji, temp2meji, temp3meji : integer; tempmej, temp2mej, temp3mej : real;
  begin
    tempmeji := 0;
    tempmej := 0;
    for i := 1 to 10836 do
      if (listap[i].prom >= tempmej) and (lb[i]) then
        begin
          tempmej := listap[i].prom;
          tempmeji := i;
        end;
    WriteLn('1. ', palabraaString(listap[tempmeji].pal));
    temp2meji := 0;
    temp2mej := 0;
    for i := 1 to 10836 do
      if (listap[i].prom >= temp2mej) and (lb[i]) and (i <> tempmeji) then
        begin
          temp2mej := listap[i].prom;
          temp2meji := i;
        end;
    WriteLn('2. ', palabraaString(listap[temp2meji].pal));
    temp3meji := 0;
    temp3mej := 0;
    for i := 1 to 10836 do
      if (listap[i].prom >= temp3mej) and (lb[i]) and (i <> tempmeji) and (i <> temp2meji) then
        begin
          temp3mej := listap[i].prom;
          temp3meji := i;
        end;
    WriteLn('3. ', palabraaString(listap[temp3meji].pal));
  end;

  function posibleCandidata (palabra1 : Palabra; s: string; palabra2 : Palabra):boolean;
  var i,j : integer; letra : char; aux : boolean; auxpal2 : AuxBools;
  begin
    aux := true;
    i := 1;
    while aux and (i < 6) do
    begin
      auxpal2[i] := true;
      letra := palabra1[i].ltr;
      if s[i] = '5' then
      begin
        aux := palabra2[i].ltr = letra; 
        auxpal2[i] := false;
      end;
      i := i + 1;
    end;
    i := 1;
    if aux then
      while aux and (i < 6) do
      begin
	      letra := palabra1[i].ltr;
	      if s[i] = '3' then
	      begin
		      if palabra2[i].ltr = letra then
		        aux := false;
          if aux then
          begin
		        auxpal2[i] := false;
		        j := 0;
	          repeat
		          j := j + 1
		        until (j = 6) or ((palabra2[j].ltr = letra) and (auxpal2[j]));
		        aux := not (j = 6);
            if aux then auxpal2[j] := false;
		        auxpal2[i] := true;
          end;
	      end;
        i := i + 1;
      end;
    i := 1;
    if aux then
      while aux and (i < 6) do
      begin
        letra := palabra1[i].ltr;
	      if s[i] = '0' then
	      begin
		      j := 0;
	        repeat
		        j := j + 1
		      until (j = 6) or ((palabra2[j].ltr = letra) and (auxpal2[j]));
		      aux := (j = 6);
        end;
        i := i + 1;
      end;
    posibleCandidata := aux;
  end;

  procedure filtrarPalabras(palabra1 : Palabra; s : string; lista : ListaPalabras; var cantpals : integer; var lb : ListaBool);
  var i : integer;
  begin
    for i := 1 to 10836 do
    begin
	    if lb[i] then
	    begin
	      lb[i] := posibleCandidata(palabra1, s, lista.pals[i]);
	      if not lb[i] then
		      cantpals := cantpals - 1;
	    end;
    end;
  end;

  procedure impPalabrasRestantes (listap : ListaProms; lb : ListaBool);
  var i : integer;
  begin
    for i := 1 to 10836 do
      if lb[i] then WriteLn(palabraaString(listap[i].pal))
  end;

  begin
	  cantpals := 10836;
    generarLista(Lista,tfIn);
    { generarMatrizPG(MatrizPG, tfIn); }
    crearListaBool(lb);
    
    { WriteLn('--Calculando promedios--');
    calcPromedios(Lista,lb,cantpals,listprom);
    WriteLn('La mejor palabra para arrancar a jugar es: ');
    imptopPalabras(listprom,lb); }

    While s1 <> 'FIN' do
    begin
      { WriteLn; }
      WriteLn('Ingrese la palabra que introdujo: ');
      WriteLn('(Ingrese "FIN" si termino)');
      ReadLn(s1);
      WriteLn('Ingrese los colores en orden: (0 = gris, 3 = amarillo, 5 = verde)');
      ReadLn(s2);
      if s1 <> 'FIN' then
      begin
        WriteLn('--Filtrando palabras--');
        filtrarPalabras(stringaPalabra(s1), s2, Lista, cantpals, lb);
        WriteLn('Quedan ', cantpals:0, ' palabras posibles');
        WriteLn('--Calculando promedios--');
        calcPromedios(Lista,lb,cantpals,listprom);
        if cantpals > 5 then
          begin
            WriteLn('Las 3 mejores palabras son:');
            imptopPalabras(listprom,lb);
          end
        else
          begin
            WriteLn('Las palabras restantes son:');
            impPalabrasRestantes(listprom,lb);
          end;
      end;   
    end;
  end.
