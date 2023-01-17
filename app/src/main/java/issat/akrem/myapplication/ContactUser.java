package issat.akrem.myapplication;

public class ContactUser {
    String id, nom, prenom, numero;

    public ContactUser() {
    }

    public ContactUser(String nom, String prenom, String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
    }

    public ContactUser(String id, String nom, String prenom, String numero) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "ContactUser{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }

}
