package com.mygame.talktofriends;

public class QuestionsRekabet {
    public String mQuesions[] = {
            "Parents should teach their children that being patient with someone does not necessarily mean they have to ____ their inappropriate behaviour.",
            "Paracetamol, one of the most effective painkillers used today, was discovered in the 1890s, but ____ how it relieves pain remains a mystery.",
            "There is a wide range of theories that attempt to _____ the differences in left-and right-handedness, some with much more empirical support than others.",
            "Psychologists have shown that people can make _____ of age, income and even personality traits based on footwear alone.",
            "The rapid growth of English as an international language has ____ a number of interesting discussions about the status of English today.",
            "Because trade among nations is so important in economic development, most countries are _____ to be able to sell their goods and services in foreign markets.",
            "Scientists in the US _____ the rivers, streams and floods of ice at the Antarctic, ______ a fascinating picture of a constantly shifting continent.",
            "_____ certain conceptual strategies can be learned, specific levels of cognitive development must be achieved in early childhood education.",
            "A report by the Japanese government _____ that the disaster in 2011 at the Fukushima Nuclear Plant ____.",
            "Adopting a positive attitude to a terrible experience can ____ enhance accuracy in emotional memories _____ diminish their negative overtones.",
            "When we think of war films, we often think of places like Vietnam and Europe, ______ the location for these films is generally the United States.",
            "In a workplace, standardization of skills involves considerable training of personnel _____ they can carry out organizational policies with few faults.",
            "Dolphins use half their brain _____ sleeps, as it gives them the ability to be on the lookout _____ danger while still technically sleeping.",
            "The discovery that the Universe _____ at an accelerating rate _____ two rival teams of scientists a Nobel Prize in Physics.",
            "______ all the essential amino acids for health and an excellent source of vitamin D, cheese is a highly nutritious food which should be included _______ any healthy diet.",
            "The crimes of the rich and the powerful can be explained ______ the same motives as any other criminal act.",
            "______, but the remains they recovered early on held little information about the birds they came from.",
            "In trying to understand the development of language,________.",
            "Thanks to vaccinations, antibiotics, sanitation and better parental care, ______.",
            "Some sorts of music strike us with their perfection, ______"



    };

    private String mChouse[][]={
            {"do away with","look down on","put up with","get up to"},
            {"adversely","precisely","increasingly","comparatively"},
            {"carry out","take over","refer to","account for"},
            {"regularities","incentives","tendencies","estimates"},
            {"confirmed","postponed","stimulated","weakened"},
            {"inconsistent","entitled","vulnerable","eager"},
            {"have mapped/painting","mapped/having been painted","had mapped/being painted","are mapping/painted"},
            {"In case","Now that","Unless","Before"},
            {"can reveal/should have been foreseen","could reveal/will be foreseen","reveals/must be foreseen","revealed/could have been foreseen"},
            {"just/as","both/and","as/as","rather/than"},
            {"only if","as","but","given that"},
            {"because","whereas","even though","so that"},
            {"during/for","in/by","at/into","on/against"},
            {"could expand/should have earned","was expanding/must have earned","will be expanding/had earned","is expanding/has earned"},
            {"For/at","With/in","Under/to","Of/from"},
            {"in terms of","prior to","apart from","instead of"},
            {"The fossil record of penguins began to improve in the late 1970s","Geographic distribution of penguins reflects a single point of origin near South America","The very first penguin fossil to be identified was a single bone found in New Zealand","Scientists have known about fossil penguins for more than 150 years"},
            {"scholars, for ages, have been debating the role played by the vocal tract and the ear","human language utilizes a fairly small number of sounds","the grammar of a language represents the linguistic knowledge or capacity of its speakers","language is a part of our essential human nature and was never invented"}, //18
            {"age is the biggest risk factor for common deadly illnesses","today, we are much more likely to die in old age than in our youth","a high childhood death rate is still a fact in most of the African states","an infant born today is not luckier than their grandparents"},
            {"as everyone cannot get the same pleasure from a certain kind of music","though we may have difficulty identifying what emotion they express","while reggae is a music style which is completely responsibility-free for the society","but artists can organize their work in such a way that only a group of people enjoy it"}


    };
    private String mCorrectAnswer[]={"put up with","precisely","account for","estimates","estimates","eager","have mapped/painting","Before","revealed/could have been foreseen","both/and","but","so that","during/for","is expanding/has earned","With/in","in terms of","Scientists have known about fossil penguins for more than 150 years","scholars, for ages, have been debating the role played by the vocal tract and the ear","today, we are much more likely to die in old age than in our youth","though we may have difficulty identifying what emotion they express"};
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
