package de.jardas.drakensang.shared.model;

import java.util.ArrayList;
import java.util.List;


public class Advantage implements Identified {
    private final String id;
    private final String name;
    private final String info;
    private final Effect[] effects;

    public Advantage(String id, String name, String info, String modifiers) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.effects = loadEffects(modifiers);
    }

    private static Effect[] loadEffects(String modifiers) {
        final String[] tokens = modifiers.trim().split("\\s*;\\s*");
        List<Effect> effects = new ArrayList<Effect>(tokens.length);

        for (String token : tokens) {
            if (token.trim().length() == 0) {
                continue;
            }

            final String[] nameAndMod = token.split(":");

            String targetName = nameAndMod[0];
            EffectTarget targetType = EffectTarget.getTargetType(targetName);
            int modifier =
                Integer.valueOf(nameAndMod[1].startsWith("+") ? nameAndMod[1].substring(1)
                                                              : nameAndMod[1]);
            effects.add(new Effect(targetType, targetName, modifier));
        }

        return effects.toArray(new Effect[effects.size()]);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Effect[] getEffects() {
        return this.effects;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + "]";
    }
}
