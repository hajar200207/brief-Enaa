import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Livre {
    String titre;
    String auteur;
    String ISBN;
    Date datePublication;
    Date dateRetourPrevu;

    public Livre(String titre, String auteur, String ISBN, Date datePublication, Date dateRetourPrevu) {
        this.titre = titre;
        this.auteur = auteur;
        this.ISBN = ISBN;
        this.datePublication = datePublication;
        this.dateRetourPrevu = dateRetourPrevu;
    }
    public void modifierLivre(String nouveauTitre, String nouvelAuteur, Date nouvelleDatePublication) {
        this.titre = nouveauTitre;
        this.auteur = nouvelAuteur;
        this.datePublication = nouvelleDatePublication;
        System.out.println("Les détails du livre ont été mis à jour avec succès.");
    }
}

class Apprenant {
    String nom;
    String adresse;
    String identifiant;
    ArrayList<Livre> livresEmpruntes;
    ArrayList<Livre> livresRendus;

    public Apprenant(String nom, String adresse, String identifiant) {
        this.nom = nom;
        this.adresse = adresse;
        this.identifiant = identifiant;
        this.livresEmpruntes = new ArrayList<>();
        this.livresRendus = new ArrayList<>();
    }

    public void emprunterLivre(Livre livre) {
        livresEmpruntes.add(livre);
        System.out.println("Le livre '" + livre.titre + "' a été emprunté par l'apprenant '" + this.nom + "'.");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Date de retour prévue : " + dateFormat.format(livre.dateRetourPrevu));
        livre.dateRetourPrevu = new Date(); // Mettre la date de retour prévue à la date actuelle
    }

    public void rendreLivre(Livre livre) {
        if (livresEmpruntes.contains(livre)) {
            livresEmpruntes.remove(livre);
            livresRendus.add(livre);
            System.out.println("Le livre '" + livre.titre + "' a été rendu par l'apprenant '" + this.nom + "'.");
        } else {
            System.out.println("L'apprenant '" + this.nom + "' ne possède pas le livre '" + livre.titre + "'.");
        }
    }
    public void modifierApprenant(String nouveauNom, String nouvelleAdresse) {
        this.nom = nouveauNom;
        this.adresse = nouvelleAdresse;
        System.out.println("Les détails de l'apprenant ont été mis à jour avec succès.");
    }
}

class Bibliotheque {
    ArrayList<Livre> livres;
    ArrayList<Apprenant> apprenants;

    public Bibliotheque() {
        this.livres = new ArrayList<>();
        this.apprenants = new ArrayList<>();
    }

    public void ajouterLivre(Livre livre) {
        livres.add(livre);
        System.out.println("Le livre '" + livre.titre + "' a été ajouté à la bibliothèque.");
    }

    public void supprimerLivre(String ISBN) {
        for (Livre livre : livres) {
            if (livre.ISBN.equals(ISBN)) {
                livres.remove(livre);
                System.out.println("Le livre '" + livre.titre + "' a été supprimé de la bibliothèque.");
                return;
            }
        }
        System.out.println("Aucun livre avec l'ISBN " + ISBN + " n'a été trouvé dans la bibliothèque.");
    }

    public void rechercherLivre(String titre) {
        for (Livre livre : livres) {
            if (livre.titre.equals(titre)) {
                System.out.println("Livre trouvé - Titre: " + livre.titre + ", Auteur: " + livre.auteur + ", ISBN: " + livre.ISBN);
                return;
            }
        }
        System.out.println("Aucun livre avec le titre '" + titre + "' n'a été trouvé dans la bibliothèque.");
    }

    public void ajouterApprenant(Apprenant apprenant) {
        apprenants.add(apprenant);
        System.out.println("L'apprenant '" + apprenant.nom + "' a été ajouté à la bibliothèque.");
    }

    public void supprimerApprenant(String identifiant) {
        for (Apprenant apprenant : apprenants) {
            if (apprenant.identifiant.equals(identifiant)) {
                apprenants.remove(apprenant);
                System.out.println("L'apprenant '" + apprenant.nom + "' a été supprimé de la bibliothèque.");
                return;
            }
        }
        System.out.println("Aucun apprenant avec l'identifiant " + identifiant + " n'a été trouvé dans la bibliothèque.");
    }
    public void emprunterLivre(String identifiantEmprunteur, String ISBNEmprunt) {
        // Recherche de l'apprenant dans la bibliothèque
        Apprenant emprunteur = null;
        for (Apprenant apprenant : apprenants) {
            if (apprenant.identifiant.equals(identifiantEmprunteur)) {
                emprunteur = apprenant;
                break;
            }
        }
        // Recherche du livre dans la bibliothèque
        Livre livreEmprunte = null;
        for (Livre livre : livres) {
            if (livre.ISBN.equals(ISBNEmprunt)) {
                livreEmprunte = livre;
                break;
            }
        }

        if (emprunteur != null && livreEmprunte != null) {
            emprunteur.emprunterLivre(livreEmprunte);
            System.out.print("Entrez la date de retour prévue (format yyyy-MM-dd) : ");
            Scanner scanner = new Scanner(System.in);
            String dateRetourStr = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                livreEmprunte.dateRetourPrevu = dateFormat.parse(dateRetourStr); // Mettre la date de retour prévue
                System.out.println("Date de retour prévue : " + dateFormat.format(livreEmprunte.dateRetourPrevu));
            } catch (ParseException e) {
                System.out.println("Format de date incorrect. Utilisez le format yyyy-MM-dd.");
            }
        } else {
            System.out.println("L'apprenant ou le livre n'a pas été trouvé.");
        }
    }



    public void rendreLivre(String ISBNRendu) {
        // Recherche du livre dans la bibliothèque
        Livre livreRendu = null;
        for (Livre livre : livres) {
            if (livre.ISBN.equals(ISBNRendu)) {
                livreRendu = livre;
                break;
            }
        }

        if (livreRendu != null) {
            // Appel de la méthode sur toutes les instances d'apprenants
            for (Apprenant apprenant : apprenants) {
                apprenant.rendreLivre(livreRendu);
            }
        } else {
            System.out.println("Le livre à rendre n'a pas été trouvé.");
        }
    }
    public void rappelRetard() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (Apprenant apprenant : apprenants) {
                    for (Livre livre : apprenant.livresEmpruntes) {
                        if (livre.dateRetourPrevu.before(new Date())) {
                            System.out.println("Rappel: Le livre '" + livre.titre + "' est en retard pour l'apprenant '" + apprenant.nom + "'.");
                            // Vous pouvez ajouter ici d'autres actions comme envoyer un e-mail, une notification, etc.
                        }
                    }
                }
            }
        };
        // Planifie l'exécution de la tâche toutes les 24 heures (86400000 millisecondes)
        timer.scheduleAtFixedRate(task, 0, 864000000);
    }

    public Apprenant findApprenantByIdentifiant(String identifiant) {
        for (Apprenant apprenant : apprenants) {
            if (apprenant.identifiant.equals(identifiant)) {
                return apprenant;
            }
        }
        return null; // Aucun apprenant trouvé avec cet identifiant
    }

    public Livre findLivreByISBN(String isbn) {
        for (Livre livre : livres) {
            if (livre.ISBN.equals(isbn)) {
                return livre;
            }
        }
        return null; // Aucun livre trouvé avec cet ISBN
    }
}
class Menu {
    private Bibliotheque bibliotheque;


    public Menu() {
        this.bibliotheque = new Bibliotheque();
    }

    public void afficherLivres() {
        System.out.println("Liste des livres dans la bibliothèque :");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Livre livre : bibliotheque.livres) {
            System.out.println("Titre: " + livre.titre + ", Auteur: " + livre.auteur + ", ISBN: " + livre.ISBN + ", Date de publication: " + dateFormat.format(livre.datePublication));
        }
    }


    public void afficherApprenants() {
        System.out.println("Liste des apprenants dans la bibliothèque :");
        for (Apprenant apprenant : bibliotheque.apprenants) {
            System.out.println("Nom: " + apprenant.nom + ", Identifiant: " + apprenant.identifiant);
        }
    }

    public void afficherLivresEmpruntes() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Livres empruntés par les apprenants :");
        for (Apprenant apprenant : bibliotheque.apprenants) {
            System.out.println("Apprenant: " + apprenant.nom);
            if (apprenant.livresEmpruntes.isEmpty()) {
                System.out.println("Aucun livre emprunté.");
            } else {
                System.out.println("Livres empruntés :");
                for (Livre livre : apprenant.livresEmpruntes) {
                    System.out.println("Titre: " + livre.titre + ", Auteur: " + livre.auteur + ", ISBN: " + livre.ISBN + ", Date d'emprunt: " + dateFormat.format(livre.dateRetourPrevu));
                }
            }
        }
    }


    public void afficherApprenantsAvecLivresRendus() {
        System.out.println("Apprenants avec les livres rendus :");
        for (Apprenant apprenant : bibliotheque.apprenants) {
            System.out.println("Apprenant: " + apprenant.nom);
            if (apprenant.livresRendus.isEmpty()) {
                System.out.println("Aucun livre rendu.");
            } else {
                System.out.println("Livres rendus :");
                for (Livre livre : apprenant.livresRendus) {
                    System.out.println("Titre: " + livre.titre + ", Auteur: " + livre.auteur + ", ISBN: " + livre.ISBN);
                }
            }
        }
    }
    public void modifierApprenant() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez l'identifiant de l'apprenant à modifier : ");
        String identifiant = scanner.nextLine();

        Apprenant apprenant = bibliotheque.findApprenantByIdentifiant(identifiant);
        if (apprenant != null) {
            System.out.print("Entrez le nouveau nom de l'apprenant : ");
            String nouveauNom = scanner.nextLine();

            System.out.print("Entrez la nouvelle adresse de l'apprenant : ");
            String nouvelleAdresse = scanner.nextLine();

            apprenant.modifierApprenant(nouveauNom, nouvelleAdresse);
        } else {
            System.out.println("Aucun apprenant trouvé avec cet identifiant.");
        }
    }

    public void modifierLivre() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez l'ISBN du livre à modifier : ");
        String ISBN = scanner.nextLine();

        Livre livre = bibliotheque.findLivreByISBN(ISBN);
        if (livre != null) {
            System.out.print("Entrez le nouveau titre du livre : ");
            String nouveauTitre = scanner.nextLine();

            System.out.print("Entrez le nouvel auteur du livre : ");
            String nouvelAuteur = scanner.nextLine();

            System.out.print("Entrez la nouvelle date de publication du livre (format yyyy-MM-dd) : ");
            String nouvelleDatePublicationStr = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date nouvelleDatePublication = dateFormat.parse(nouvelleDatePublicationStr);
                livre.modifierLivre(nouveauTitre, nouvelAuteur, nouvelleDatePublication);
            } catch (ParseException e) {
                System.out.println("Format de date incorrect. Les détails du livre n'ont pas été mis à jour.");
            }
        } else {
            System.out.println("Aucun livre trouvé avec cet ISBN.");
        }
    }
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";
    public void afficherMenu() {
        System.out.println("*************************** Application la Gestion de Bibliothèque dans l'école ENAA***************************");
        System.out.println("***************************************************************************************************************");
        System.out.println(ANSI_YELLOW + " ----------Bienvenue Menu----------");
        System.out.println(ANSI_YELLOW + " =====Bienvenue dans la bibliothèque!=====");
        System.out.println(ANSI_YELLOW + " =*=*=*=*=Espace Admin!*=*=*=*=");
        System.out.println(ANSI_YELLOW + " =====Livre!=====");
        System.out.println(ANSI_WHITE+"1. Ajouter un livre");
        System.out.println("2. Supprimer un livre");
        System.out.println("3. Modifier le livre");
        System.out.println("4. Rechercher un livre");
        System.out.println(ANSI_YELLOW + " =====Apprenant!=====");
        System.out.println(ANSI_WHITE+"5. Ajouter un apprenant");
        System.out.println("6. Supprimer un apprenant");
        System.out.println("7. Modifier l'apprenant");
        System.out.println(ANSI_YELLOW + " =====Reservation livre =====");
        System.out.println(ANSI_WHITE+"8. Emprunter un livre");
        System.out.println("9. Rendre un livre");
        System.out.println("10. Activer le rappel de retard");
        System.out.println(ANSI_YELLOW + " =*=*=*=*=Espace Bibliothèque!*=*=*=*=");
        System.out.println(ANSI_WHITE+"11. Afficher la liste des livres");
        System.out.println("12. Afficher la liste des apprenants");
        System.out.println("13. Afficher les livres empruntés par les apprenants");
        System.out.println("14. Afficher les livres rendus");
        System.out.println(ANSI_YELLOW + " =*=*=*=*=Espace Sortie!*=*=*=*=");
        System.out.println(ANSI_WHITE+"0. Quitter");
        System.out.println(ANSI_RESET+"***************************************************************************************************************");
        System.out.println(ANSI_RESET+"***************************************************************************************************************");
        System.out.println(ANSI_RESET+"**Choisissez une option:**");

    }

    public void executerOption(int choix) {
        Scanner scanner = new Scanner(System.in);
        switch (choix) {
            case 1:
                System.out.print("Entrez le titre du livre : ");
                String titre = scanner.nextLine();
                System.out.print("Entrez l'auteur du livre : ");
                String auteur = scanner.nextLine();
                System.out.print("Entrez l'ISBN du livre : ");
                String ISBN = scanner.nextLine();
                System.out.print("Entrez la date de publication du livre (format : yyyy-MM-dd) : ");
                String datePublicationStr = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datePublication = null;
                try {
                    datePublication = dateFormat.parse(datePublicationStr);
                } catch (ParseException e) {
                    System.out.println("Format de date invalide. Utilisation du moment actuel.");
                    datePublication = new Date(); // Utilisation de la date actuelle par défaut
                }
                Livre nouveauLivre = new Livre(titre, auteur, ISBN, datePublication, new Date());
                bibliotheque.ajouterLivre(nouveauLivre);
                break;
            case 2:
                System.out.print("Entrez l'ISBN du livre à supprimer : ");
                String ISBNASupprimer = scanner.nextLine();
                bibliotheque.supprimerLivre(ISBNASupprimer);
                break;
            case 3:
                // Modifier un livre
                modifierLivre();
                break;
            case 4:
                System.out.print("Entrez le titre du livre à rechercher : ");
                String titreARechercher = scanner.nextLine();
                bibliotheque.rechercherLivre(titreARechercher);
                break;
            case 5:
                System.out.print("Entrez le nom de l'apprenant : ");
                String nomApprenant = scanner.nextLine();
                System.out.print("Entrez l'adresse de l'apprenant : ");
                String adresseApprenant = scanner.nextLine();
                System.out.print("Entrez l'identifiant de l'apprenant : ");
                String identifiantApprenant = scanner.nextLine();
                Apprenant nouvelApprenant = new Apprenant(nomApprenant, adresseApprenant, identifiantApprenant);
                bibliotheque.ajouterApprenant(nouvelApprenant);
                break;
            case 6:
                System.out.print("Entrez l'identifiant de l'apprenant à supprimer : ");
                String identifiantASupprimer = scanner.nextLine();
                bibliotheque.supprimerApprenant(identifiantASupprimer);
                break;
            case 7:
                // Modifier un apprenant
                modifierApprenant();
                break;
            case 8:
                System.out.print("Entrez l'identifiant de l'apprenant qui emprunte le livre : ");
                String identifiantEmprunteur = scanner.nextLine();
                System.out.print("Entrez l'ISBN du livre à emprunter : ");
                String ISBNEmprunt = scanner.nextLine();
                bibliotheque.emprunterLivre(identifiantEmprunteur, ISBNEmprunt);
                break;
            case 9:
                System.out.print("Entrez l'ISBN du livre à rendre : ");
                String ISBNRendu = scanner.nextLine();
                bibliotheque.rendreLivre(ISBNRendu);
                break;
            case 10: // Nouveau choix pour activer le rappel de retard
                bibliotheque.rappelRetard();
                System.out.println("Les rappels de retard ont été activés.");
                break;
            case 11:
                afficherLivres();
                break;
            case 12:
                afficherApprenants();
                break;
            case 13:
                afficherLivresEmpruntes();
                break;
            case 14:
                afficherApprenantsAvecLivresRendus();
                break;
            case 0:
                System.out.println("Merci d'avoir utilisé le système de la bibliothèque. Au revoir !");
                System.exit(0);
            default:
                System.out.println("Option invalide. Veuillez choisir une option valide.");
        }
    }
    public static void main(String[] args) {
        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            menu.afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer le caractère de nouvelle ligne

            menu.executerOption(choix);
        }
    }
}

