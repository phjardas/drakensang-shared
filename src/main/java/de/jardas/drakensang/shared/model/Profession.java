package de.jardas.drakensang.shared.model;

public final class Profession implements Identified {
    private final String id;
    private final int lebensenergieModifikator;
    private final int ausdauerModifikator;
    private final int astralenergieModifikator;
    private final int magieresistenzModifikator;

    public Profession(String id, int lebensenergieModifikator, int ausdauerModifikator,
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

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + getId() + "]";
    }
}
