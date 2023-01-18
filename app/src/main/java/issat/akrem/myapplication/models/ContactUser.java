package issat.akrem.myapplication.models;

public class ContactUser {
    public String id, nom, prenom, numero, createdBy;

    public ContactUser() {
    }

    public ContactUser(String nom, String prenom, String numero, String createdBy) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.createdBy = createdBy;
    }

    public ContactUser(String id, String nom, String prenom, String numero, String createdBy) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "ContactUser{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
