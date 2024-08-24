package com.mygame.talktofriends;

public class Questions4 {
    public String mQuesions[] = {
            "Pete sometimes _______ tea in the breakfast but he usually ________ coffee. He ___________ milk at the moment. He is hard to understand.",
            "A: What are you doing now?\n" +
                    "\n" +
                    "B: I ___________ PUBG as i usually do in my free time.\n" +
                    "\n" +
                    "A: Hmm, Ok. We _________ for a picnic in half an hour. Would you like to come?\n" +
                    "\n" +
                    "B: I _________________, maybe.\n" +
                    "\n" +
                    "A: Let me know, then.\n" +
                    "\n" +
                    "B: Sure thing.",
            "The baby___________ at the moment.",
            "Sahra is a new student at school so she ______________ everybody in the class.",
            "Please leave me alone! I ___________ to do my homework.",
            "Mr. Nelson ___________ meat because he is a vegetarian.",
            "The weather _________ nice today. It often __________, but today it ______________.",
            "The weather is very nice today. So we can swim ______________.",
            "I can't speak English yet. ________ i don't study hard enough,  ___________ i must study harder ________ do my homework assignments that Serdar Hoca gives.",
            "A: What are your plans after you leave university?\n" +
                    "\n" +
                    "B: I _____________ work in a hospital in Africa.",
            "Look up, Mary! Can you see the dark clouds? It ___________ rain soon.",
            "My family _______ large. I __________ 5 uncles, 10 aunts and a lot of cousins. I like ___________ time with them.",
            "Call _____________ as soon as possible. I want to talk to __________.",
            "Why are you looking at ________? Do you know _______?",
            "John is a very smart kid. He learns Japanese by ___________.",
            "1-   Melt the butter in the pan and put the mixture in it.\n" +
                    "\n" +
                    "2-  First, break the eggs and mix for a while.\n" +
                    "\n" +
                    "3- Today I'm going to teach you how to make an omelette.\n" +
                    "\n" +
                    "4- Then, add some cheese and salt.\n" +
                    "\n" +
                    "5-  Finally, cook it for 5 minutes. Enjoy your omelette.",
            "A: What's the date?\n" +
                    "\n" +
                    "B: It's ________________. (22-11)",
            "A: Hey! Are you coming to the concert?\n" +
                    "\n" +
                    "B: I am not sure. I ___________ come with you. It's up to my father.",
            "Because of the bad weather conditions, very ---- people are able to attend the meeting.  Should we cancel it?",
            "I was in London this summer. I made ____________ friends there. I didn't have __________ problems with them. So i had a happy summer."



    };

    private String mChouse[][]={
            {"drink/drink/is drinking","drinks/drinks/is drinking","is drinking/is drinking/drinks","is drinking/drinks/drinks"},
            {"am playing/are going/don't know","play/are going/don't know","am playing/are going/am not knowing","am playing/will go/don't know"},
            {"not sleeping","is not sleeping","doesn't sleep","aren't sleeping"},
            {"knows","is knowing","doesn't know","isn't knowing"},
            {"try","am try","am trying","am not trying"},
            {"isn't eating","is eating","eats","doesn't eat"},
            {"is/rains/doesn't rain","-/rains/isn't raining","is/rains/isn't raining","is/is raining/is raining"},
            {"but we can dive","or we can go fishing","and we can watch TV","so we can't play football"},
            {"So/because/or","Because/so/and","But/and/and","So/so/and"},
            {"-","will","am going to","going to"},
            {"is going to","isn't going to","will","won't"},//11
            {"are/have got/spending","is/have got/spending","is/has got/to spend","are/have got/to spend"},
            {"i/you","me/your","me/you","him/you"},
            {"him/him","me/i","them/me","he/he"},
            {"myself","herself","himself","hisself"},
            {"2-4-5-1-3","2-3-4-5-1","3-2-4-1-5","3-5-4-2-1",},
            {"November twenty two","twenty two of November","The November of twenty second","The twenty second of November"},
            {"should","can","might","have to"},
            {"many","little","a few","few"},
            {"some/some","many/any","a lot of/any","few/many"}







    };
    private String mCorrectAnswer[]={"drinks/drinks/is drinking","am playing/are going/don't know","is not sleeping","doesn't know","am trying","doesn't eat","is/rains/isn't raining","or we can go fishing","Because/so/and","am going to","is going to","is/have got/spending","me/you","him/him","himself","3-2-4-1-5","The twenty second of November","might","few","a lot of/any"};
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
