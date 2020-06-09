cd leopard-boot-parent-default/example
svn up
mvn clean
mvn archetype:create-from-project
cd target/generated-sources/archetype
mvn deploy
