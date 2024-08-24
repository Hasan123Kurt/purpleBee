package com.mygame.talktofriends;

public class Question3 {
    public String mQuesions2[] = {
            "My parents are__________ poetry.",
            "Sarah can't stand_____________ alone.",
            "My father and my mother enjoy _________________ up late at the weekends.",
            "They __________________ watching horror films. They prefer comedies.",
            "Mr. and Mrs. Malcolm have a lot of books. They are crazy about____________",
            "A: What do you like doing in your free time?\n" +
                    "\n" +
                    "B: _______________________________.",
            "Kate always carries her Ipod wih her. She ________________ to songs.",
            "It's very hot today. ____________________ swimming. Do you want _____________ with me?",
            "John _____________ to come to dinner with us.",
            "A: What kind of a car _____________?\n" +
                    "\n" +
                    "B: Ford Mustang.",
            "Bring me _________ umbrella and __________ jacket. It's raining outside.",
            "My brother is __________ University student.",
            "Ahmet is ________ nice boy. But he isn't ________ polite.\n" +
                    "\n" +
                    "What is the right combination?",
            "There is ______ nice dress in this shop. I want to buy _____ dress.",
            "I don't like __________ dogs, but I like my brother's dog.",
            "In 1824, Louis Braille developed ______ system of writing for _____ blind. Quickly, _____system, known as \"Braille\" after ____ inventor, spread from________France to dozens of _____ countries. ",
            "There _______ sugar in my coffee.",
            "Don't buy______ butter. We have __________ in the fridge.",
            "I need __________ information about the city.",
            "I always have _________ egg for breakfast.",





    };

    private String mChouse2[][]={
            {"interested in","interested at","fond at","keen to"},
            {"eat","eating","to eat","to eating"},
            {"to get","get","getting","to getting"},
            {"like","love","dislike","enjoy"},
            {"selling","buying","going","reading"},
            {"I am interested in football.","I like playing football.","I hate doing homework.","I want to be a doctor."},
            {"can't stand listening","hates listening","enjoys listening","dislikes listening"},
            {"I would like to go/to come","I like going/coming","I would like to go/coming","I like to going/coming"},
            {"would like","likes","can't stand","enjoys"},
            {"has your father got","hasn't your father","you have got","has your parents"},
            {"an/a","a/an","the/an","-/a"},
            {"a","an","the","-"},
            {"a/a","-/-","a/-","-/a"},
            {"the/the","-/-","a/the","the/a"},
            {"the","a","an","-"},
            {"the/the/the/the/-/-","a/the/-/the/-/-","a/the/the/the/-/-","a/the/the/the/the/-"},
            {"is","are","is a","are a"},
            {"a/any","some/some","any/some","some/any"},
            {"some","any","an","a"},
            {"some","an","a","any"},















    };
    private String mCorrectAnswer2[]={"interested in","eating","getting","dislike","reading","I like playing football.","enjoys listening","I would like to go/to come","would like","has your father got","an/a","a","a/-","a/the","-","a/the/the/the/-/-","is","any/some","some","an"};
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
