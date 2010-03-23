package de.jardas.drakensang.shared.model;

import org.apache.commons.lang.StringUtils;

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
        if (StringUtils.isBlank(modifiers)) {
            return new Effect[0];
        }

        final String[] tokens = modifiers.trim().split("\\s*;\\s*");
        List<Effect> effects = new ArrayList<Effect>(tokens.length);

        for (String token : tokens) {
            if (token.trim().length() == 0) {
                continue;
            }

            final String[] nameAndMod = token.split(":");

            final String targetName = nameAndMod[0].trim();
            final EffectTarget targetType = EffectTarget.getTargetType(targetName);
            final String modifierValue = nameAndMod[1].trim();
            final int modifier =
                Integer.valueOf(modifierValue.startsWith("+") ? modifierValue.substring(1)
                                                              : modifierValue);
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
