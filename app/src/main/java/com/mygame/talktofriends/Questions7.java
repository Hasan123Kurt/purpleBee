package com.mygame.talktofriends;

public class Questions7 {
    public String mQuesions[] = {
            "My brother ______________________ in Chicago since he was born, but he is planning to finally change his location soon.",
            "A: ___________ you _________ David lately? I can't find him anywhere.\n" +
                    "\n" +
                    "B: Sorry, i ______________ him.",
            "A: I finished all my work today. I _____________________ on a holiday two days later.\n" +
                    "\n" +
                    "B: Are you sure? The hotels seem to be very expensive these days.\n" +
                    "\n" +
                    "A: I have already booked a hotel.\n" +
                    "\n" +
                    "B: OK! Have fun then.",
            "You are interested in ancient history and you want to do a project on Sumerians but you do not have the sources you need. So you ask your history teacher: ",
            "Father    : What do you mean? You crashed the car again?\n" +
                    "\n" +
                    "Son        : ----\n" +
                    "\n" +
                    "Father    : I'm sure it was. This is the fourth accident you have had this year.\n" +
                    "\n" +
                    "Son        : You're quite angry now, dad. Why don't we talk about this later on.",
            "You would like to buy a present for your best friend's daughter's birthday, but you have difficulty in choosing a pleasant gift. So you ask your friend for help: ",
            "Complete the question.\n" +
                    "\n" +
                    "What would you do if there _____ an earthquake?",
            "Jane _____________ basketball better if she ______________ taller.",
            "If Tyrion Lannister _______________ (not) so sleepy, he __________________ so tired (not).",
            "Even if Tim ______________ the richest man in the world, he ____________ lend you any money, either.",
            "If only i ________ a kitten or a puppy, but my parents don't allow pets at home.",
            "A: What _____________ happen if we didn't pay the rent this month, dad?\n" +
                    "\n" +
                    "B: The house owner _____________ kick us out, son.",
            "A: I ____________ speak with him unless he ______________.\n" +
                    "\n" +
                    "B: Has he?\n" +
                    "\n" +
                    "A: No!",
            "I can't play PubG because i don't have a computer. If only i _____________ a computer.",
            "Which car______________ you _____________ if you had a lot of money?",
            "Which sentence is NOT true for the second conditional?",
            "I would come to your wedding if i ______________ so busy. Sorry my friend.",
            "1- I'm so bored.\n" +
                    "\n" +
                    "2- Why not!\n" +
                    "\n" +
                    "3- But first, let me explain; each letter has different point values, depending on the rarity of the letter.\n" +
                    "\n" +
                    "4- Ok, let's start.\n" +
                    "\n" +
                    "5- Would you like to play scrabble?",
            "Students study ......... if they take a break approximately every hour because they come back with refreshed minds.",
            "If you ......... me for suggestions before you leave, I ......... you some nice restaurants around here."


    };

    private String mChouse[][]={
            {"is living","lives","lived","has lived"},
            {"Did/see/didn't see","Have/seen/didn't","Have/saw/haven't seen","Have/seen/haven't seen"},
            {"have gone","will go","am going","went"},
            {"Excuse me,what do you think of Sumerians?","I need some documents on ancient history. Have you got any?","What books do you have you can lend me?","Do you know how Sumerians write books?"},
            {"I am sorry, dad. I promise it will never happen again.","But believe me it wasn't my fault.","Was it worth the money you had wasted?","Why do you ask? Don't you know it's too late now?"},
            {"Will you please give me an idea about the gift for your daughter?","I can't decide what i will buy?","What if i don't buy a present?","Why don't you buy her a bicycle?"},
            {"could","would","be","were"},
            {"plays/will be","would play/was","played/would be","plays/is"},
            {"isn't/won't be","doesn't/isn't","wasn't/wouldn't be","wasn't/would be"},
            {"was/wouldn't","was/would","is/won't","will be/won't"},
            {"had","have","didn't have","don't have"},
            {"was/would","would/-","would/would","will/will"},
            {"wouldn't/apologised.","won't/apologises","will/doesn't apologise","won't/wouldn't apologise"},
            {"were","have","had","didn't have"},
            {"buy/would","would/buy","will/buy","buy/will"}, //15
            {"You can use it for daydreaming and wishing.","It describes an imaginary situation.","It describes what will happen in the future.","It's not likely to to happen."},
            {"was","were","weren't","wouldn't"},
            {"5-1-2-4-3","5-3-1-2-4","1-5-2-4-3","1-5-4-2-3"},
            {"more efficient","more efficiently","the most efficient","efficient enough"},
            {"asked/would recommend","ask/will recommend","are asking/can recommend","will ask/will recommend"}




    };
    private String mCorrectAnswer[]={"has lived","Have/seen/haven't seen","am going","I need some documents on ancient history. Have you got any?","But believe me it wasn't my fault.","Will you please give me an idea about the gift for your daughter?","were","would play/was","wasn't/wouldn't be","was/wouldn't","had","would/would","won't/apologises","had","would/buy","It describes what will happen in the future.","weren't","1-5-2-4-3","more efficiently","ask/will recommend"};
    public String getquestions(int a){
        String question = mQuesions[a];
        return question;
    }
    public String getChoice(int a){
        String choice =mChouse[a][0];
        return choice;
    }
    public String getChoice1(int a){
        String choice =mChouse[a][1];
        return choice;
    }
    public String getChoice2(int a){
        String choice =mChouse[a][2];
        return choice;
    }
    public String getChoice3(int a){
        String choice =mChouse[a][3];
        return choice;
    }


    public String getcorrectAnswer(int a){
        String answer = mCorrectAnswer[a];
        return answer;
    }
}
