package de.jardas.drakensang.shared.db;

import de.jardas.drakensang.shared.model.Advantages;
import de.jardas.drakensang.shared.model.CasterRace;
import de.jardas.drakensang.shared.model.CasterType;
import de.jardas.drakensang.shared.model.Identified;
import de.jardas.drakensang.shared.model.IntegerMap;
import de.jardas.drakensang.shared.model.Person;
import de.jardas.drakensang.shared.model.Regenerating;
import de.jardas.drakensang.shared.model.Sex;
import de.jardas.drakensang.shared.model.Sonderfertigkeit;
import de.jardas.drakensang.shared.model.Sonderfertigkeiten;

import org.apache.commons.lang.WordUtils;

import java.math.BigDecimal;
import java.math.MathContext;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;


public final class DaoHelper {
    public static void load(Person person, ResultSet result)
        throws SQLException {
        person.setId(result.getString("Id"));
        person.setName(result.getString("Name"));
        person.setLookAtText(result.getString("LookAtText"));

        person.setSex(Sex.valueOf(result.getString("Sex")));
        person.setRace(RaceDao.valueOf(result.getString("Race")));
        person.setCulture(CultureDao.valueOf(result.getString("Culture")));
        person.setProfession(ProfessionDao.valueOf(result.getString("Profession")));
        person.setMagician(result.getInt("IsMagicUser") == 1);
        person.setCasterType(CasterType.valueOf(result.getString("CasterType")));
        person.setCasterRace(CasterRace.valueOf(result.getString("CasterRace")));

        person.setSneakSpeed(round(result.getDouble("SneakSpeed"), 4));
        person.setWalkSpeed(round(result.getDouble("WalkSpeed"), 4));
        person.setRunSpeed(round(result.getDouble("RunSpeed"), 4));
        person.setCurrentSpeed(round(result.getDouble("CurrentSpeed"), 4));

        load(person.getAttribute(), result);
        load(person.getTalente(), result);
        load(person.getZauberfertigkeiten(), result);

        load(person.getSonderfertigkeiten(), result);
        load(person.getAdvantages(), result);
    }

    public static void load(Sonderfertigkeiten list, ResultSet result)
        throws SQLException {
        for (Sonderfertigkeit e : SonderfertigkeitDao.values()) {
            final int value = result.getInt(e.getAttribute());

            if (value >= 0) {
                list.add(e);
            }
        }
    }

    public static void load(Advantages list, ResultSet result)
        throws SQLException {
        final String[] tokens = result.getString("Advantages").split("\\s*;\\s*");

        for (String token : tokens) {
            if (token.length() > 0) {
                list.add(AdvantageDao.valueOf(token));
            }
        }
    }

    public static void load(Regenerating reg, ResultSet result)
        throws SQLException {
        reg.setCurrentValue(result.getInt(reg.getName()));
        reg.setRegenerationAmount(result.getInt("Reg_" + reg.getName()));
        reg.setRegenerationFrequency(result.getInt("Reg_" + reg.getName() + "_freq"));
        reg.setRegenerationFrequencyCombat(result.getInt("Reg_" + reg.getName() + "_freq_combat"));

        if (reg.hasBonus()) {
            reg.setBonus(result.getInt(reg.getName() + "bonus"));
        }
    }

    public static void load(IntegerMap map, ResultSet result)
        throws SQLException {
        for (String key : map.getKeys()) {
            map.set(key, result.getInt(key));
        }
    }

    public static Set<String> parseList(String string) {
        final String[] tokens = string.split("\\s*[,;]\\s*");
        final Set<String> ret = new HashSet<String>(tokens.length);

        for (int i = 0; i < tokens.length; i++) {
            ret.add(tokens[i]);
        }

        return ret;
    }

    public static <I extends Enum<I>> Set<I> parseList(String string, Class<I> type) {
        final Set<String> items = DaoHelper.parseList(string);
        final Set<I> val = new HashSet<I>(items.size());

        for (String item : items) {
            val.add(Enum.valueOf(type, item));
        }

        return val;
    }

    public static double round(double input, int precision) {
        return BigDecimal.valueOf(input).round(new MathContext(precision)).doubleValue();
    }

    public static void store(UpdateStatementBuilder builder, IntegerMap values) {
        for (String key : values.getKeys()) {
            builder.append("'" + key + "' = ?", values.get(key));
        }
    }

    public static String serialize(Iterable<?extends Identified> items) {
        final StringBuilder str = new StringBuilder();

        for (Identified item : items) {
            if (str.length() > 0) {
                str.append(";");
            }

            str.append(item.getId());
        }

        return str.toString();
    }

    public static void store(UpdateStatementBuilder builder, Sonderfertigkeiten sonderfertigkeiten) {
        for (Sonderfertigkeit sf : SonderfertigkeitDao.values()) {
            builder.append("'" + sf.getAttribute() + "' = ?",
                sonderfertigkeiten.contains(sf) ? 1 : (-500));
        }
    }

    public static void store(Regenerating reg, UpdateStatementBuilder builder) {
        builder.append("'" + reg.getName() + "' = ?", reg.getCurrentValue());
        builder.append("'Reg_" + reg.getName() + "' = ?", reg.getRegenerationAmount());
        builder.append("'Reg_" + reg.getName() + "_freq' = ?", reg.getRegenerationFrequency());
        builder.append("'Reg_" + reg.getName() + "_freq_combat' = ?",
            reg.getRegenerationFrequencyCombat());
        builder.append("'" + reg.getName() + "max' = ?", reg.getMaxValue());

        if (reg.hasBonus()) {
            builder.append("'" + reg.getName() + "bonus' = ?", reg.getBonus());
        }
    }

    public static void store(Person person, UpdateStatementBuilder builder) {
        store(builder, person.getAttribute());
        store(builder, person.getTalente());
        store(builder, person.getSonderfertigkeiten());
        store(builder, person.getZauberfertigkeiten());

        builder.append("'Sex' = ?", person.getSex().name());
        builder.append("'Race' = ?", person.getRace().getId());
        builder.append("'Culture' = ?", person.getCulture().getId());
        builder.append("'Profession' = ?", person.getProfession().getId());
        builder.append("'IsMagicUser' = ?", person.isMagician() ? 1 : 0);
        builder.append("'CasterType' = ?", person.getCasterType().name());
        builder.append("'CasterRace' = ?", person.getCasterRace().name());
        builder.append("'Advantages' = ?", serialize(person.getAdvantages()));
        builder.append("'SneakSpeed' = ?", person.getSneakSpeed());
        builder.append("'WalkSpeed' = ?", person.getWalkSpeed());
        builder.append("'RunSpeed' = ?", person.getRunSpeed());
        builder.append("'CurrentSpeed' = ?", person.getCurrentSpeed());

        store(person.getLebensenergie(), builder);
        store(person.getAstralenergie(), builder);
        store(person.getAusdauer(), builder);
        // save(character.getKarma(), builder);
        builder.append("ATbasis = ?", person.getAttackeBasis().getValue());
        builder.append("PAbasis = ?", person.getParadeBasis().getValue());
        builder.append("FKbasis = ?", person.getFernkampfBasis().getValue());
        builder.append("MR = ?", person.getMagieresistenz().getValue());

        // FIXME Attacke
        // FIXME Parade
        // FIXME Ausweichen
        // FIXME Behinderung
        final String anim = person.getRace().isDwarf() ? "dwarf" : person.getSex().name();
        builder.append("'Graphics' = ?", "characters/" + anim);
        builder.append("'AnimSet' = ?", anim);
        builder.append("'PickingPhysics' = ?", "characters/" + person.getSex().name() +
            "/skeleton");
        builder.append("'SoundSet' = ?", getSoundSet(person));
    }

    private static String getSoundSet(Person person) {
        if (person.getRace().isElf()) {
            return "archetyp_" + person.getSex().name();
        } else if (person.getRace().isDwarf()) {
            return "archetyp_dwarf";
        }

        return "default" + WordUtils.capitalize(person.getSex().name());
    }
}
