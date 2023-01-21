package com.example.testquizz;

import java.util.ArrayList;
import java.util.List;

public class VariantManager {
    private List<VariantData> list = new ArrayList<>();
    private int level = 0;

    private int trueCount = 0;
    private int wrongCount = 0;

    public VariantManager(List<VariantData> list) {
        this.list = list;
    }

    private VariantData getVariant() {
        return list.get(level);
    }

    public String getQuestion() {
        return getVariant().getQuestion();
    }

    public String getVariantA() {
        return getVariant().getVariants()[0];
    }

    public String getVariantB() {
        return getVariant().getVariants()[1];
    }

    public String getVariantC() {
        return getVariant().getVariants()[2];
    }

    public boolean checkAnswer(String userAnswer) {

        if (level != list.size()) {
            boolean isTrue = getVariant().getAnswer().equalsIgnoreCase(userAnswer);

            if (isTrue) {
                trueCount++;
            } else {
                wrongCount++;
            }
        }

        boolean hasNext = hasVariant();

        if (hasVariant()) {
            level++;
        }

        return hasNext;

    }

    public int getTrueCount() {
        return trueCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }
    public int getTotalQuestions(){return list.size();}

    private boolean hasVariant() {
        return level + 1 < list.size();
    }


    public int getLevel() {
        return level;
    }
}
