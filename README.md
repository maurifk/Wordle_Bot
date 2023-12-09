# Wordle_Bot
 *Wordle solvers iterated over different languages and algorithms.*
This is my summer holidays project. Since Summer 2022, with having only taken my first programming class, I made the first version in Pascal, the only language I knew.
Each solver has 3 different languages, Spanish, English and "Filtered" English, being the last one a subset of English with only the words the original Wordle used.
 

 1. **Pascal Solver**

    The first one I made. It was done with just half a year of experience programming. It clearly is not the best code, but I do not want to update since it clearly shows my progression. Misses a lot of QOL features, like it being on three different files with the only difference being the language it plays on, the need to execute again the application to restart the game and that any unexpected input breaks it. Still, it can play give the 3 best words at each turn. 
    The algorithm it uses to choose the words consists on filtering the words using the previous colors and then it matches every word `a` left against every other word left `b` and based on the colors Wordle would return if `a` was played and the correct answer was `b`. Then, it orders the words by the scores it got in total and returns the best 3. It takes around 4.5 turns to solve a Wordle game.
    The best 5 words it always returns for the first turn in Spanish are:

    1. AIREO
    2. ROSEA
    3. OREAS
    4. AUREO
    5. CAREO
	 
2. **C++ Solver**

    This version I made it on Summer 2023, armed with two courses on C++ programming I decided to remake the Pascal version, mainly focused on improving QOL aspects, since I had gather a lot more experience programming and knew better ways to achieve the same. This version works with the same algorithm, but you can select the language when you first open it, the first time a language is chosen, a matrix is saved on a "<language>_matriz.txt" making the next runs a lot faster, it drops from taking around 85 seconds to give the first words, to just 11 seconds. Also, after the third turn, it gives the option to restart the bot.
    The algorithm is almost identical to the one in the Pascal version, however, it is easier to change the scores given to each color, so it allows for better testing. The Pascal version was fixed on 5 points for a green and 3 points for a yellow, numbers I just invented since they seemed . In the C++ version, with these weights it gives the following 5 words for the first turn, almost identical to the ones given by Pascal and by transitive, it also takes around 4.5 turns to solve a game:

 	1. ROSEA
    2. AIREO
    3. AREAS
    4. SARAO
    5. AIREA

    However, with the internal scorings changed to 5 points a green, 1 point yellow, the words change, and it tends to play better, solving it in around 3.8 turns:

    1. CAREO
    2. SARAO
    3. CARAS
    4. CAREA
    5. CARAO
 
3. **Summer 2024 plans**

    The plans going forward are to, firstly, improve the algorithm, since it has considerable flaws, for example, it can not play a word it has already discarded, a strategy worth considering for earlier turns since it can return valuable information. The idea of static scores also does not seem fully right, since it has a lot of ties that are being solved randomly. My plan with this project is not to just copy others algorithms, so the algorithms are my own tests and possibly, not the best strategies. This will be done in Java, since it is the language I am most comfortable in. Also, this will include parallelization, an area I had no knowledge before the courses I took in 2023. Then, the next idea is to create a bot which plays the game automatically, allowing me to better assess different strategies and measure the average number of turns it takes for each strategy to win.
    This opens the opportunity to make a bot that automatically learns and does not use any "hard-coded" strategy, allowing me  to observe it's decisions and try to understand them and try to see a pattern.
		






	 




