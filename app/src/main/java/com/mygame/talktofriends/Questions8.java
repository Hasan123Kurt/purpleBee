package com.mygame.talktofriends;

public class Questions8 {
    public String mQuesions[] = {
            "If the classroom .......... with an overhead projector, it.......... lessons more interesting.",
            "You asked your friend something about the computers but he is giving you too much information and they are not related to the question you asked. So you say;",
            "Sally: I need to go to the airport immediately. Do you mind if i take your car?\n" +
                    "\n" +
                    "Tim: ____________________________________. (he agrees.)",
            "You invited your friend for dinner but he seems very shy because your parents are also at home. So you want to make her comfortable.\n" +
                    "\n" +
                    "What would you say?",
            "I ___________my homework before i _____________ to the next chapter of the course.",
            "When i _______________ to the class, the teacher ___________________ there. He was angry with me because i was late.",
            "After we __________________ breakfast with my family, my sister ______________ the dishes.",
            "I ______________ a cup of coffee while my sister ________________ the latest news.",
            "Which companies _______________ with before you _____________ for this position?",
            "A: Which company __________________ on the stock market after ASELSAN?\n" +
                    "\n" +
                    "B: I have no idea.",
            "Mom: Did you take your pills? You know, it's important.\n" +
                    "\n" +
                    "Son: Yes, mom. I _____________ my pills before dinner.",
            "When I got home, the children had already ___ to bed.",
            "____________ in the forest is an activity i enjoy the most.",
            "My father gave up _____________ last year. He is healthier now. He ___________ a lot.",
            "I love ___________ to this restaurant at the weekends because it's right in the middle of the nature. I actually want ___________ here all the time.",
            "My hobbys are ___________________ and _______________________.",
            "A: Tim, we don't have enough eggs _____________ an omlete. I want you _______________ to the supermarket to buy some eggs and _______________ buy anything else!",
            "There is no point in ______________ the same mistakes all over again and ________________ different results",
            "I can't bear ____________ alone in this life. Everybody needs somebody.",
            "A: Would you mind _____________ the TV off? I am trying to concentrate for my exam.\n" +
                    "\n" +
                    "B: Sure. Would you like me _________ anything else?\n" +
                    "\n" +
                    "A: No, thanks."


    };

    private String mChouse[][]={
            {"had equipped/could have made","were equipped/would made","would be equipped/would made","equipped/would made"},
            {"What's wrong with you?","Get out of here!","It's better than nothing","Let's get to the point"},
            {"Are you crazy?","That sounds good.","Don't mention it.","Be my guest"},
            {"Don't worry!","What a shame!","Make yourself home","Take it easy."},
            {"did/had started","had done/started","didn't do/start","did/will start"},
            {"came/had already been","had come/was already","came/wasn't","came/hadn't been already"},
            {"had breakfast/washed","didn't have/washed","had had breakfast/washed","hadn't had/have washed"},
            {"had/had read","had had/read","was having/was reading","had/was read"},
            {"had you work/applied","you had worked/applied","did you work/apply","had you worked/applied"},
            {"did you invest","had you invested","do you invest","will you invest"},
            {"had taken","took","hadn't taken","didn't take"},
            {"gone","went","going","was going"},
            {"To run","Running","To running","To running","Run"},
            {"smoking/used to smoke","smoking/smoked","to smoke/used to smoke","to smoking/smokes"},
            {"to come/to be","coming/being","coming/to be","to come/being"},
            {"playing computer games/going out","to play computer games/to go out","playing computer games/to go out","play computer games/go out"},//16
            {"to make/go/not to","making/to/don't","to make/to go/not to","making/going/not buying"},
            {"doing/expecting","doing/to expect","to do/to expect","to do/expecting"},
            {"to be","being","be","i am"},
            {"to turn/to do","turning/doing","to turn/doing","turning/to do"}
    };
    private String mCorrectAnswer[]={"were equipped/would made","Let's get to the point","Be my guest","Make yourself home","had done/started","came/had already been","had had breakfast/washed","was having/was reading","had you worked/applied","will you invest","took","gone","Running","smoking/used to smoke","coming/to be","playing computer games/going out","to make/to go/not to","doing/expecting","being","turning/to do"};
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
