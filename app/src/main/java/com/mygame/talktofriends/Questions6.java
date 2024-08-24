package com.mygame.talktofriends;

public class Questions6 {
    public String mQuesions[] = {
            "1)usually - 2)at weekends - 3)play computer games- 4)Cherry and Alex - 5)stay up late - 6)and ",
            "My family and i like travelling a lot. We _______________ (be) to different countries and we ___________________ (meet) a lot of different people. However, we _________________ (enjoy) Turkey most. Therefore, since 2010 my family and i ______________ (spend) our summer holidays in Turkey.",
            "David is on a school trip. He has ____________ to Paris. They ___________ there last sunday.",
            "___________________ the books to the library yet?",
            "A: What _____________ for dinner, mum? It smells delicious.\n" +
                    "\n" +
                    "B: Your favourite one. Meatballs and french fries.",
            "Sandra has lived in this flat for _____________.",
            "Have / your / grandparents / lately / you / visit",
            "A: Dad ________________ called me.\n" +
                    "\n" +
                    "B: What? Have you ______________ to him?\n" +
                    "\n" +
                    "A: Yes, he is coming home.",
            "A: ________________________________ this song a million times. Stop playing it over and over!\n" +
                    "\n" +
                    "B: Get out of my room then, this way you  ____________ hear it.",
            "A: Our neighbours _______________ on holiday last week.\n" +
                    "\n" +
                    "B: So they came back?\n" +
                    "\n" +
                    "A: Yes, but they ___________________ to another place for more holiday.",
            "Khalid   : Do you fancy going for a bike ride?\n" +
                    "\n" +
                    "Gemma : ----\n" +
                    "\n" +
                    "Khalid   : The city park would be peaceful now.",
            "What ___________ do if you don't get the job?",
            "If you ________ this skirt, i ___________ buy it, too.",
            "If we ___________ the plants, they ___________.",
            "If the temperature __________ below 0' C degree, water ____________.",
            "If i am late for dinner, ______________. Start eating.",
            "We won't go on holiday ________________ enough money.",
            "If you read more books and watch a lot of movies in English, __________________.",
            "____________, you will be late for school.",
            "If you need money, ___________________.\n" +
                    "\n" +
                    "\n" +
                    "\n"


    };

    private String mChouse[][]={
            {"2-1-3-6-5-4","2-1-4-3-6-5","4-3-6-5-1-2","4-1-3-6-5-2"},
            {"were/have met/enjoyed/have spent","have been/met/enjoyed/have spent","have gone/met/enjoyed/have spent","have been/met/enjoyed/spent"},
            {"been/went","gone/went","been/have gone","gone/have gone"},
            {"Did Claire return","Did Claire returned","Has Claire return","Has Claire returned"},
            {"have you cooked","have you cook","did you cook","did you cooked"},
            {"she was born.","2010","ten years","last year"},
            {"Have you lately visited your grandparents?","Have you visited your grandparents lately?","Have you visit your grandparents lately?","Have lately you visited your grandparents?"},
            {"-/already talked","has just called me/already talked","has just called me/already spoken","just has called me/already spoken"},
            {"You listen to/don't have to","You listened to/can't","You have listened to/won't have to","You have listened to/can't"},
            {"were/have recently gone","were/have recently been","went haven't recently gone","have just gone/have just gone"},
            {"The rain may start any moment. Are you serious?","Sure. Where do you plan to go for a ride?","I still haven't been able to learn how to ride a bike.","Absolutely. Let's start preparing the horse first."},
            {"won't you do","do you do","will you do","didn't you do"},
            {"will buy/buy","buy/won't buy","will buy/will buy","buy/will buy"},//13
            {"don't water/will die.","don't water/die","water/die","won't water/will die"},
            {"drop/freeze","drops/will freeze","drops/freezes","will drop/will freeze"},
            {"you won't wait for me","will you wait for me","i don't have dinner","don't wait for me"},
            {"if we won't save","if we save","if we will save","unless we save"},
            {"your English is better","your English will be worse","you won't improve your English","you can improve your English"},
            {"Unless you don't hurry","If you hurry","If you don't hurry","If you won't hurry"},
            {"I don't have any money","will you work","we can eat out","ask David"}



    };
    private String mCorrectAnswer[]={"4-1-3-6-5-2","have been/met/enjoyed/have spent","gone/went","Has Claire returned","have you cooked","ten years","Have you visited your grandparents lately?","has just called me/already talked","You have listened to/won't have to","were/have recently gone","Sure. Where do you plan to go for a ride?","will you do","buy/will buy","don't water/die","drops/freezes","don't wait for me","unless we save","you can improve your English","If you don't hurry","ask David"};
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
