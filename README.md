# NoteDeFrais
Ismail KHEIRY - projet Java EE - 4ème EXPSIE Ingetis

Cette application a été codée et testée dans un PC avec OS Windows 10 uniquement.
Pour la tester et/ou l'adapter à un autre système d'exploitation il faudrat changer
une petite partie du code surtout celle qui concerne les repertoires des images.

Pour Tester l'application il faut:
  - avoir les installations nécessaires pour exécuter un programme java (JDK...)
  - avoir eclipse déjà installé (de préférence eclipse oxygen)
  - créer une base de données vide en locale avec ce nom "note_de_frais" ou bien changer le fichier 
    application.properties comme suit :
      remplacer "note_de_frais" dans spring.datasource.url=jdbc:mysql://localhost:3306/note_de_frais?useSSL=false
      par spring.datasource.url=jdbc:mysql://localhost:3306/NOM_DE_VOTRE_BASE_DE_DONNEES?useSSL=false
      où NOM_DE_VOTRE_BASE_DE_DONNEES repésente le nom de votre base de données.
  - changer les identifiants de l'accès à la base de données comme suit:
      spring.datasource.username=USERNAME   où   USERNAME est celui de l'utilsateur de base de données 
      spring.datasource.password=PASSWORD   où   PASSWORD est celui de l'utilsateur de base de données
  - créer un repertoire "NoteDeFrais" dans le repertoire "Documents" de l'utilisateur actuel
     exemple : Sous windows C:\Users\ikheiry\Documents\NoteDeFrais
               où C:\Users\ikheiry représente le repertoire de l'utilisateur actuel (session)
               



Pour des suggestions concernants le projet : ismailkhairi876@yahoo.fr
