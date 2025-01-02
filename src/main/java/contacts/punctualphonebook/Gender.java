package contacts.punctualphonebook;

enum Gender {
    M, F;

    @Override
    public String toString() {
        return name();
    }
}
