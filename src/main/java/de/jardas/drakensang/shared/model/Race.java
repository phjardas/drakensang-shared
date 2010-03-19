package de.jardas.drakensang.shared.model;

public final class Race implements Identified {
    private final String id;
    private final int lebensenergieModifikator;
    private final int ausdauerModifikator;
    private final int astralenergieModifikator;
    private final int magieresistenzModifikator;
    private final int karmaModifikator = 0;

    public Race(String id, int lebensenergieModifikator, int ausdauerModifikator,
        int astralenergieModifikator, int magieresistenzModifikator) {
        super();
        this.id = id;
        this.lebensenergieModifikator = lebensenergieModifikator;
        this.ausdauerModifikator = ausdauerModifikator;
        this.astralenergieModifikator = astralenergieModifikator;
        this.magieresistenzModifikator = magieresistenzModifikator;
    }

    public String getId() {
        return id;
    }

    public int getKarmaModifikator() {
        return karmaModifikator;
    }

    public int getLebensenergieModifikator() {
        return lebensenergieModifikator;
    }

    public int getAusdauerModifikator() {
        return ausdauerModifikator;
    }

    public int getAstralenergieModifikator() {
        return astralenergieModifikator;
    }

    public int getMagieresistenzModifikator() {
        return magieresistenzModifikator;
    }

    public boolean isElf() {
        return "Elf".equals(getId());
    }

    public boolean isDwarf() {
        return "Zwerg".equals(getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + "]";
    }
}
