package com.mygame.talktofriends;

public class Questions2 {
    public String mQuesions2[] = {
            "Our house________ close. It takes 2 minutes to get there.",
            "My father / every day/ to work / goes / at 8:00 am",
            "_________ do you wake up?\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "I wake up at 7:30 am. ",
            "_________ is your brother?\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "He is great, thanks for asking. ",
            "_________ do you take English class?\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Because I want to improve my speaking. ",
            "Choose the correct answer using simple present tense!\n" +
                    "\n" +
                    "Sandra __________ milk and she never drinks it.",
            "The students ____________ to the school on foot because their houses are next to the shool.",
            "What time _____ he________?",
            "German people ____________ having information about the work before they _________ it.",
            "Claire: The train ____________ the station at 8:00 am. The plane ____________ at 10:00 am. You must choose one of them.\n" +
                    "\n" +
                    "Tom: Ok, i prefer the train.",
            "Christian and Kate _______________ as English teachers in UK.",
            "I work from Monday to Friday. I never work ________________.",
            "A: Do you__________ eat in bed?\n" +
                    "\n" +
                    "B: No, ____________.",
            "We____________ lies to our teachers.",
            "Dad __________________to work on Sundays.",
            "Stacy is a singer. She loves to sing. She is in a band. She sings in the band. She is the lead singer. Sometimes she plays the piano.  \n" +
                    "\n" +
                    "What does she always do in the band?",
            "Dean is Chad and Stacy's friend. He is also in the band. He stands next to Chad. He plays bass guitar. Dean does not sing. He does not like singing. \n" +
                    "\n" +
                    "Why doesn't Dean sing?",
            "The band practices three times a week. They mostly perform at nightclubs. Sometimes they sing at weddings. They are a very good band. \n" +
                    "\n" +
                    "What does the band usually do?",
            "Which one is NOT correct?",
            "Choose the correct combination!\n" +
                    "\n" +
                    "Children _____________ eating icecream but they _____________ doing their homework."



    };

    private String mChouse2[][]={
            {"isn't","are","aren't","is"},
            {"My father work to goes at 8:00 am every day","My father goes at 8:00 am to work every day","My father goes to work every day at 8:00 am","My father goes every day to work at 8:00 am"},
            {"which","where","when","what"},
            {"When","What","How many","How"},
            {"Why","What time","When","Which"},
            {"likes","like","don't like","doesn't like"},
            {"don't go","doesn't go","goes","go"},
            {"do/wake up","does/wake up","does/wakes up","do/wakes up"},
            {"like/do","likes/do","like/does","don't like/does"},
            {"leave/take of","leave/takes off","leaves/take off","leaves/takes off"},
            {"works","working","are","work"},
            {"on Saturdays","on Tuesdays","on Wednesdays","on Thursdays"},
            {"never/ever","ever/never","ever/always","never/always"},
            {"tell never","never tell","never tells","tells never"},
            {"don't usually go","doesn't go usually","doesn't usually go","doesn't never go"},
            {"She plays the piano.","She sings","She loves to sing","She is a singer"},
            {"Because he doesn't like singing.","Because he can't sing.","Because he plays the bass guitar.","Because Stacy is the singer in the band, he is not."},
            {"The band practices three times a week","They mostly perform at nightclubs","Sometimes they sing at weddings","They are a very good band"},
            {"I went to London last june","He's coming back next Tuesday","I go home every Easter","We will call you in this evening"},
            {"likes/dislikes","dislike/like","like/likes","like/dislike"}








    };
    private String mCorrectAnswer2[]={"is","My father goes to work every day at 8:00 am","when","How","Why","doesn't like","go","does/wake up","like/do","leaves/takes off","work","on Saturdays","ever/never","never tell","doesn't usually go","She sings","Because he doesn't like singing.","They mostly perform at nightclubs","We will call you in this evening","like/dislike"};
    public String getquestions(int a){
        String question = mQuesions2[a];
        return question;
    }
    public String getChoice(int a){
        String choice =mChouse2[a][0];
        return choice;
    }
    public String getChoice1(int a){
        String choice =mChouse2[a][1];
        return choice;
    }
    public String getChoice2(int a){
        String choice =mChouse2[a][2];
        return choice;
    }
    public String getChoice3(int a){
        String choice =mChouse2[a][3];
        return choice;
    }


    public String getcorrectAnswer(int a){
        String answer = mCorrectAnswer2[a];
        return answer;
    }
}
