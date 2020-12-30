Dependencies:
	Web: La dépendance de spring-web contient des utilitaires communs spécifiques au web pour les environnements Servlet et Portlet.
	JPA: Cette dépendance traite de la prise en charge améliorée des couches d'accès aux données basées sur l'APP. Il facilite la création d'applications Spring qui utilisent les technologies d'accès aux données.
	Hibernate: Hibernate est utilisé pour accéder à la couche de données et Struts est utilisé pour les cadres Web.
	H2: C'est un système de gestion de base de données relationnelle open source créé entièrement en Java. Il peut être intégré dans des applications Java ou fonctionner en mode client-serveur.
	DevTools: Le but du module est d'essayer d'améliorer le temps de développement tout en travaillant avec l'application Spring Boot. Spring Boot DevTools récupère les modifications et redémarre l'application.
	ThymeLeaf: Thymeleaf est une bibliothèque basée sur Java utilisée pour créer une application web. Elle fournit un bon support pour servir un XHTML/HTML5 dans les applications web.

Nous avons paramétré l'url d'appel /greeting avec : @GetMapping("/greeting").
Nous avons choisi le fichier html à afficher avec : return "greeting";
Nous envoyons le nom qui nous disons bonjour avec : @RequestParam(name="nameGET", required=false, defaultValue="World") String nameGET.

La différence est l'ajout de la table ADDRESS.
L'ajout de la table ADDRESS est dû au fait qu'on a ajouté une classe Adress dans le package en tant qu'entité (@Entity).

En relançant l'application et avec une requête select on voit tout le contenu de data.sql.

L'annotation @Autowired permet d'injecter la dépendance de l'objet implicitement. 

Bootstrap:
	j'ai créé un nouveau fichier html que j'ai nommé "navbar.html" j'ai choisi une template d'une navra que j'ai trouvé sur internet, je l'ai mis dans le fichier html dans une balise div thyme leaf fragment pour l'utiliser dans les autres pages.

Oui il faut une clé API pour utiliser l'API OpenWeatherMap.
On utilise l'url : api.openweathermap.org/data/2.5/weather
On utilise la méthode GET.
On passe les parameters d'appel ainsi: api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}&units={unit}
	{lat} est la latitude.
	{lon} est la longitude.
	{API key} est la clé API.
	{unit} est l'unité de mesure.
La température est dans main temp.
