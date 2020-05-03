# Atelier_Microservices_Analysis

<H2>A. Contexte</H2>
Nous nous intéressons dans ce projet à l’analyse des processus métier. Ces derniers
sont utilisés au sein des entreprises pour décrire les processus internes ou les
collaborations inter-entreprises permettant la réalisation d’une tâche ou d’un logiciel. La
norme BPMN 2.0 est actuellement la notation standard pour la modélisation des processus
métier.
Le but principal de BPMN est de fournir une notation qui soit facilement
compréhensible par tous les utilisateurs de l'entreprise, depuis les analystes métier qui
créent les ébauches initiales des processus, jusqu'aux développeurs responsables de mettre
en place la technologie qui va exécuter les processus applicatifs correspondants. Mais aussi
jusqu'aux utilisateurs de l'entreprise qui vont mettre en œuvre ces processus.
BPMN vise de plus à rendre l'orchestration des processus "exécutable"
(automatisable) par une transposition des modèles en langage BPEL.

<H2>B. Objectif</H2>
L’objectif du projet est de proposer un environnement générique et extensible basé
sur des micro services permettant l’analyse formelle de processus métier.

<H2>C. Architecture</H2>
L’architecture se concrétise par une séparation entre Front End et Back End.
Le Front End propose une interface utilisateur basée sur bpmn.io pour interagir avec
les différentes API en back end.
Le Back End, quant à lui, propose différents micro services indépendants et
communiquant via des requêtes API Rest. Un micro service passerelle (ou Gateway) servira
de façade concernant les fonctionnalités utilisateurs.
Cette interface haut niveau se chargera d’orchestrer d’autres API se chargeant de la gestion
des modèles, utilisateurs, etc. Une séparation entre différentes API permet un faible
couplage, une maintenance facilitée et l’extension facilitée de l’architecture en place.

B. Domaines API
Les domaines fonctionnels sont les suivants :

 <ul>
  <li> <b>Modèle API </b> <br>
Le modèle contient tout ce qui touche à un diagramme BPMN comme son propriétaire, son
nom, le fichier en format XML, etc..
L’utilisateur client doit pouvoir consulter, déposer, modifier ou supprimer un modèle.
La communication du modèle se fera à l’aide de post JSON.</li>
  <li><b>User API</b><br>
Le domaine user est lié à la gestion des utilisateurs. En ce sens, l’utilisateur client doit
pouvoir créer un utilisateur, afficher la liste des utilisateurs et rechercher un utilisateur
spécifique ou le supprimer.</li>
  <li><b>Session API</b><br>
Le domaine session est lié à la gestion des sessions. L’utilisateur doit pouvoir travailler dans
une session de travail, créée à sa connexion, pour réaliser ses opérations. Cette dernière
n’existe plus après les opérations réalisées.
Une session concerne un ou plusieurs modèles et ses accès concurrentiels, L’architecture
devra interdire les modifications sur un modèle pour lequel une session est en cours.</li>
  <li><b>Annuaire API</b><br>
Le domaine annuaire répertorie dynamiquement l’ensemble des domaines ainsi que leurs
actions sous formes de requêtes. L’utilisateur peut ainsi voir l’ensemble des requêtes HTTP
possibles dans l’architecture des micro services. Ces requêtes sont récupérées à travers la requête OPTIONS dans chaque domaine.</li>
  <li><b>Gateway API</b><br>
Le domaine Gateway joue un rôle primordial de contrôleur. Il fournit une façade à l’utilisateur
et redirige l’action à l’aide du domaine annuaire vers l’action du domaine correspondant.
En plus de jouer le rôle de médiateur, il informe l’utilisateur sur le fonctionnement de
l’interface de programmation. Il permet aussi de faire la passerelle en faisant le lien entre le
micro service utilisateur et ses modèles. Lorsque l’accès est validé, une session de travail
est créé.
Pour éviter les temps de réponse, l’architecture doit prendre en compte des threads pour
éviter les requêtes bloquantes avec un retour qui joue le rôle de notification.</li>
  <li><b>Analyse API</b><br>
Le domaine analyse est le cœur du métier. Il comprend toute les actions liées à la gestion
du modèle, calcul et analyse. En ce sens, il doit permettre d’appeler la création d’une
session, de pouvoir appeler les méthodes définies pour la gestion d’un modèle dans le
domaine modèle.
Il n’existe, à ce jour pas de service permettant d’analyser un diagramme bpmn et d’obtenir
un rapport détaillé quant à sa qualité.
Le but du projet étant de proposer un environnement générique et extensible afin d’intégrer
ce type de service.</li>
</ul> 

<H2>D. Installation</H2>

<H3>1. Environnement de développement</H3>
MongoDB : système de gestion de bases de données NoSQL.<br>
IntelliJ : IDE<br>
Postman : test logiciel impliquant le test d'API<br>
Maven : outil de gestion et de compréhension de projet logiciel<br>
GitHub : service d’hébergement de gestion de développement de logiciel<br>
Spark : micro framework pour la création d’application web<br>

<H3>2. Mise en place du projet</H3>
 <ul>
  <li><b>MongoDB :</b>
     <ul>
      <li>Pour reprendre les sources du projet, vous aurez besoin d’installer MongoDB.<br>
      Pour Windows, consultez le lien suivant:
      https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/<br>
      MongoDB pour macOS :
      https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/</li>
      <li>Lancer MongoDB (ligne de commande) :
        <pre>
        <code>
            net start MongoDB
        </pre>
        </code>
      </li>
      <li>Arreter MongoDB :
        <pre>
        <code>
            net stop MongoDB
        </pre>
        </code>
      </li>
     </ul> 
  </li>

  <li><b>Tutoriels</b>
   <ul>
    <li>Java Spark API Rest Server :<br>
      http://www.the-lazy-dev.com/fr/spark-pour-les-debutants-creer-des-api-rest-full-avec-java-et-mongodb/<br>
      http ://sparkjava.com/tutorials/<br>
      http://sparkjava.com/documentation#routes<br>
      https://www.mscharhag.com/java/building-rest-api-with-spark<br>
      http://www.baeldung.com/spark-framework-rest-api<br>
      https://community.filemaker.com/docs/DOC-8871<br>
    </li>
    <li>Unirest API Rest Client<br>
      http://unirest.io/java.html<br>
      http://www.baeldung.com/unirest</li>
   </ul> 
 </li>
  
  <li><b>Requête HTTP :</b><br>
  Tutoriel OpenClassrooms : https://openclassrooms.com/courses/les-requetes-http
  </li>
  
  <li><b>Tester les requêtes HTTP pour API :</b><br>
    <ul>
      <li>Adresse par défaut : <br> http://localhost:4567/</li>
      <li>Chrome : Postman<br>
      https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop</li>
      <li>Firefox : RESTClient<br>
      https://addons.mozilla.org/fr/firefox/addon/restclient/</li>
      <li>POST Json<br>
        <pre>
          <code>
          {
            "attribut_classe"  = "valeur_attrtibut",
            "attribut_classe2" = "valeur_attrtibut2"
          }
          </code>
        </pre> 
      </li>
    </ul> 
  </li>
  
</ul> 
