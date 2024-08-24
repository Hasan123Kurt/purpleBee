package com.mygame.talktofriends;

public class Questions {
    public String mQuesions[] = {
      "I am a big fan of Rihanna. Her full name is Robyn Rihanna Fenty, but her fans know her as Rihanna. She was born on 20th February, 1988. She is tall. She is 173 cm. She is really slim and fit. Her natural hair color is dark brown, but she dyes it very different colors. \n" +
              "\n" +
              "Which question does not have the answer in the text?",
            "Britney : What is your occupation?\n" +
                    "\n" +
                    "Tim       : I build skyscrapers.\n" +
                    "\n" +
                    "Britney : Isn't it a difficult job?\n" +
                    "\n" +
                    "Tim       : Yes, that's true but after finishing the building, seeing the magnificient scenery from the top is very ejoyable.\n" +
                    "\n" +
                    "We can understand that Tim is a/ an - - - - .",
            "Which sentence below is wrong?",
            "Choose the correct answer! \n" +
                    "\n" +
                    "............ are very good people.",
            "Tom and Sarah...... best friends.",
            "In which sentence can you write is?",
            " Which question is NOT correct?",
            "Choose the correct answer!\n" +
                    "\n" +
                    "Brad Pitt..... a singer. He.... an actor.",
            "A: ...................................?\n" +
                    "\n" +
                    "B: No, we aren't from Manchester. We are from London",
            "This..... best friend, Tom.",
            "A:___________ your best friends at school?\n" +
                    "\n" +
                    "B: Ayşe and Ali.",
            "Which sentence is correct?",
            "Which word goes in the space? \n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "Can you look after the ___ rabbit while we are on holiday?",
            "I have got a dog. _____ colour is white.\n" +
                    "\n" +
                    "Which one comes to the space?",
            "My sister lives in Berlin with ____ boyfriend.",
            "We have got a lot of neighbours in Bebek. __________ neighbours are rich because ___________ have got a lot of money.",
            "Are New York and Los Angeles Spanish Cities?  - No, ___________ Spanish cities.",
            "I ____________ a computer. ________ hard drive is broken. Can you fix it?",
            "Your father's sister is your.....?",
            "Your mother and your father got divorced. Your mother is now married to Ahmet. He has got a daughter and 2 sons.\n" +
                    "\n" +
                    "Who are they for you? (children)"


    };

    private String mChouse[][]={
        {"Where is she from?","What is her full name?","How tall is she?","What does she look like?"},
            {"tailor","mechanic","engineer","cook"},
            {"A barber cuts hair","A greengrocer sells fruit","A pharmacist sells medicine","A baker sells meat"},
            {"His friend","Her family","Her parents","Their neighbour"},
            {"am","is","are","isn't"},
            {"This___my mother","___you a doctor?","___your parents here?","___she and he twins?"},
            {"Where is your house?","How old are you?","Is Tom here?","You are a maneger?"},
            {"is/isn't","is/is","isn't/is","isn't/isn't"},
            {"Are we from Manchester?","Where do you come from?","Are you from London?","Are you from Manchester?"},
            {"is my","my is","your are","are your"},
            {"who is","who are","Are","Is"},
            {"That's Jacks car","That's of Jack car","That's Jack car's","That's Jack's car"},
            {"children's","childrens'","children","children'"},
            {"My dog","It","Its","It's"},
            {"his","his'","her","their"},
            {"We/they","Our/their","Our/They","Your/we"},
            {"They aren't","It is","It isn't","They are"},
            {"am/It's","is/Its","'s have got/Their","have got/Its"},
            {"stepsister","uncle","sister in law","aunt"},
            {"a sister in law and 2 brothers in law","a sister and two brothers","a stepsister and 2 stepsons","a stepsister and 2 stepbrothers"}

    };
    private String mCorrectAnswer[]={"Where is she from?","engineer","A baker sells meat","Her parents","are","This___my mother","You are a maneger?","isn't/is","Are you from Manchester?","is my","who are","That's Jack's car","children's","Its","her","Our/They","They aren't","have got/Its","aunt","a stepsister and 2 stepbrothers"};
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
