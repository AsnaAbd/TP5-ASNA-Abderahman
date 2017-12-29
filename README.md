#etudiantsLinkedIn TP5_ASNA

# Télécharger le projet

Pour télécharger le projet en local, ouvrez une console et tapez la commande suivante : 
git clone https://github.com/AsnaAbd/etudiantsLinkedIn.git

ou bien télécharger directement le projet depuis github

# Build & development

1. Dans phpmyadmin créer une base de données : db_etudiants
2. Ouvrez votre IDE eclipse et importer le projet téléchargé précédemment dans votre workspace
3. Allez sur /etudiantsLinkedIn/src/main/java/org/opendevup/TpSpringMvcApplication.java
4. Ensuite Run TpSpringMvcApplication

# Accès depuis le navigateur
Accès à votre application depuis :
http://localhost:8092/login

# Gestion de la sécurité 
1. Pour vous connecter la première fois vous devez ajouter des utilisateurs dans la table users par exemple :

	username : admin
	password :123
	actived : 1 
	username : et1
	password :123
	actived : 1 
	username : inv1
	password :123
	actived : 1 
2. Ensuite allez dans la table role et créez des rôles :

	ADMIN
	ETUDIANT
	INVITE
3. Dans la table users_role affectez les roles aux utilisateurs par exemple:

user_username	 roles_role :
admin 			 ADMIN
et1 			 ETUDIANT
inv1			 INVITE
