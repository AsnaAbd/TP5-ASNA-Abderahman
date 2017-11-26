function connexion() {
    var login = document.forms["formconnect"]["login"].value;
    var pass = document.forms["formconnect"]["password"].value;
    if (login == "admin@univ.fr" && pass == "admin") {
        location="profiles.html";
        return false;
    }
    if (login == "etudiant@univ.fr" && pass == "etudiant") {
      location="editProfil.html";
      return false;
    }
}
