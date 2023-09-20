# LabsVirtuales - Stage 3
Este es el repositorio de mi TFG, necesario para hacer un seguimiento detallado de mi progreso en el despliegue de Laboratorios Virtuales en un entorno Cloud.

En esta tercera fase debemos crear los YAML para los deployment y services que desplegarán la arquitectura en Kubernetes con la imagen que obtendrán directamente de nuestro repositorio Docker (https://hub.docker.com/repositories/gontorres26). A continuación se describen lo cambios a realizar con respecto al stage 3:

 - Se ha creado una carpeta (InfraDomain): que contiene los archivos YAML con toda la infraestructura que pretendemos desplegar en Kubernetes.
 - Se ha añadido un microservicio más a nuestro modelo, Gateway que nos permite centralizar la comunicación entre nuestro microservicios.

Hasta hora nuestros microservicios se comunicaban directamente entre ellos pero si pretendemos añadir nuevas funcionalidades, la complejidad aumenta y dificulta la comunicación. Las pruebas de esta fase se han hecho en minikube con la intención de no consumir recursos de la nube y por practicar en un entorno de prueba

Carpetas:

  1. g.torres.2017-LabsVirtuales: carpeta con el proyecto objeto de este TFG.
