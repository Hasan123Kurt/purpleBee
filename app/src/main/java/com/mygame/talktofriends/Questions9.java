package com.mygame.talktofriends;

public class Questions9 {
    public String mQuesions[] = {
            "You seem _________ very angry. What happened?",
            "Iphone X has recently been very expensive. I have struggled ___________  money for a long while, but, because of the recent devaluation of Turkish Lira, i can't afford ____________ an Iphone X, maybe second hand Iphone 6s.",
            "I thought of you as the first person ______________ this goal.",
            "I can't figure out this damn machine. Do you know what _____________?",
            "I miss ___________ in the travel industry. Maybe I can get my old job back.",
            "A lot of people look for the cheapest driving school without ......... how important the quality of the training is.",
            "I didn't want ........ in his private business, but he looked so miserable that I couldn't help ....... him if everything was all right.\n" +
                    "\n",
            "People speak English all over the world.\n" +
                    "\n" +
                    "The passive of this sentence is:...................................................",
            "My father doesn't cook dinner at home, my mother cooks.\n" +
                    "\n" +
                    "What is the passive of this sentence?",
            "Tim _____________ (ask) me to be in front of the cinema at 20:00 o'clock. That's where i'm going right now.",
            "The books _______________ from the library. There were right there.",
            "There was an accident yesterday and three people ______________ to the hospital.",
            "Mona Lisa ____________ by Leonarda Da Vinci.",
            "A:Who ________________ the phone?\n" +
                    "\n" +
                    "B: Alexander Graham Bell.",
            "If you are not eating that piece of cake, i will ______________ eat it.",
            "It was very hard but finally i _____________ beat cancer.",
            "I ___________________ provide everything they need for my family for a long time, it is not gonna change anytime soon!\n" +
                    "\n" +
                    "\n" +
                    "\n",
            "And for ________ scuba divers, we would like to direct your attention to _________ resort's dive center,__________ five-star facility and ________ best way to explore ____________ Pihuahani Reef just offshore.",
            "Dad bought mum a special ring for their wedding anniversary. It is very expensive because it _________ diamond.",
            "Mary ________ that she would call me tomorrow."


    };

    private String mChouse[][]={
            {"to be","be","being","been"},
            {"saving/buying","to save/to buy","saving/to buy","to buy/saving"},
            {"to achieve","achieveing","achieve","to achiveing"},
            {"we do","should we do","to do","doing"},
            {"working","work","to work","not to work"},
            {"realising","to realise","to have realised","being realised"},
            {"to interfere/asking","to have interfered/to have asked","to be interfering/being asked","interfere/having asked"},
            {"People are spoken English all over the world.","English are spoken all over the world by people.","People spoken are English all over the world.","English is spoken all over the world."},
            {"My father isn't cooked by dinner at home, is my mother cooked.","Dinner isn't cooked by my father and mother.","Dinner isn't cooked by my father, is cooked by mother.","Dinner isn't cooked by mother, cooked by my father"},
            {"was asked","is asked","asked","ask"},
            {"were taken","was taken","wasn't taken","weren't taken"},
            {"are taken","took","were taken","weren't taken"},
            {"is drawen","was drawn","were drawen","is drawn"}, //13
            {"was on","invested","was invented","were invested"},
            {"happy","happly","glad","gladly"},
            {"would","can","was able to","used to"},
            {"was able to","could","will be able to","have been able to"},
            {"the/the/a/the/the","-/the/a/the/the","-/the/a/the/-","a/the/the/the/the"},
            {"is made by","is made of","makes of","makes by"},
            {"said me","says me","told me","tell me"},





    };
    private String mCorrectAnswer[]={"to be","to save/to buy","to achieve","to do","working","realising","to interfere/asking","English is spoken all over the world.","Dinner isn't cooked by my father, is cooked by mother.","asked","weren't taken","were taken","was drawn","invested","gladly","was able to","have been able to","-/the/a/the/the","is made of","told me"};
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
