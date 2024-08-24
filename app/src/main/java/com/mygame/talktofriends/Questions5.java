package com.mygame.talktofriends;

public class Questions5 {
    public String mQuesions[] = {
            "He thinks that _____ love will save the world.",
            "Alisa: I would love to go paragliding. I just love it so much.\n" +
                    "\n" +
                    "How does she feel like about paragliding?",
            "I watched ________ video on youtube. Then, i watched _______ video you sent me. Both were fun.",
            "Mom: Hey Lise. What are you doing?\n" +
                    "\n" +
                    "Lisa : Hey mom. I am trying to talk with a tourist but i ______________ what he says. His accent is very bad.",
            "A:___________ see each other tomorrow, darling?\n" +
                    "\n" +
                    "B: Yes, meet me at the restaurant at 8:00 pm.",
            "You spend too much money. You __________ spend so much, you ___________ save some.",
            "A: Excuse me. _____________ i leave my dog here or ____________ take my dog on the train?\n" +
                    "\n" +
                    "B: No, you __________ take your dog on the train.",
            "A: Hey man! Why are you so upset?\n" +
                    "\n" +
                    "B: My house owner kicked me out of the house.\n" +
                    "\n" +
                    "A: Why did he do so?\n" +
                    "\n" +
                    "B: Because i ________ pay my rent.\n" +
                    "\n" +
                    "A: That's sad but you know, you _________ pay your rent.\n" +
                    "\n" +
                    "B: I know but i am broke nowadays.",
            "You _____________ wear your seat-belt or you ________ die in case of an accident!\n" +
                    "\n" +
                    "Choose the best combination!",
            "Mother: It's cold outside. You _________ take your jacket with you.",
            "The worst thing about living alone is that i ___________ wash my clothes by myself. I hate washing clothes.",
            "A: _______________ with my English homework, dad?\n" +
                    "\n" +
                    "B: Of course. What is your homework about?",
            "You _____________ drink too much coke. It's bad for your health.",
            "You got 25 out of 100 from the exam, ________ you failed. ___________ you will have another chance next month.",
            "I ______________ every day but today i _____________ because i feel sick.",
            "My brother _____________ a lot of friends but he _____________ any real friends. I feel sorry for him.",
            "A: Sister, i am under the bed. __________________ me?\n" +
                    "\n" +
                    "B: No, i can't see you.\n" +
                    "\n" +
                    "___________________\n" +
                    "\n" +
                    "A: What are you doing now?\n" +
                    "\n" +
                    "B: I _______________ the doctor. I will call you later.",
            "___________ finish your project last month?",
            "I __________ walk under the rain when i was young but not anymore, because i get sick.",
            "There _________ any apples in the frigde, so i went to the market to buy some."


    };

    private String mChouse[][]={
            {"the","a","an","-"},
            {"She is interesting in paragliding","She is keen on paragliding","She likes paragliding","She is crazy about paragliding"},
            {"the/the","a/a","the/a","a/the"},
            {"am not understanding","don't understand","won't understand","am not understand"},
            {"Can't","have to","Are you supposed to","Will we be able to"},
            {"shouldn't/should","should/shouldn't","mustn't/must","can/can't"},
            {"Can/must/can't","Must/can/can't","Can/must/mustn't","Should/must/can't"},
            {"can't/have to","couldn't/have to","wouldn't/have to","didn't/can"},
            {"have to/must","can/might","must/might","should/should"},
            {"must","have to","ought to","can"},
            {"mustn't","can","should","must"},  //11
            {"Must you help me","Do you have to help me","Can i help you","Can you help me"},
            {"not ought to","shouldn't","don't have to","can't"},
            {"so/but","so/so","because/but","but/so"},
            {"am working out/am not working out","work out/don't work out","don't work out/am working out","work out/am not working out"},
            {"hasn't got/hasn't got","has got/hasn't got","hasn't got/has got","have got/haven't got"},
            {"Do you see/am seeing","Are you seeing/am seeing","Do you see/see","Are you see/am seeing"},
            {"Can you","Will you","Were you able to","Would"},
            {"was able to","could","used to","am used to"},
            {"was","were","wasn't","weren't"}





    };
    private String mCorrectAnswer[]={"-","She is crazy about paragliding","a/the","don't understand","Will we be able to","shouldn't/should","Must/can/can't","couldn't/have to","must/might","ought to","must","Can you help me","shouldn't","so/but","work out/am not working out","has got/hasn't got","Do you see/am seeing","Were you able to","used to","weren't"};
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
