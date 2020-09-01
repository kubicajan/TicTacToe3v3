##### Requirements
- Java JDK 11
- Maven 3.6.2 or any other version

##### Before you start
- Execute following command in order to install external library to your local maven repository:
```mvn initialize```

##### Provided classes
- ```GameConfiguration``` - represents game configuration with values which are necessary for game
- ```PlayerSymbol``` - represents player symbol which can be placed on game board
  - X - just cross
  - O - just circle
  - E - empty space
- ```Point``` - model represents _row_, _column_ pair. 

##### Game board properties 
- Field with coordinates [_row_=0; _column_=0] refers to left upper corner
- Game board size can be obtained from: ```gameConfiguration.getBoardSize()```
- Length of winner row can be obtained from ```gameConfiguration.getWinnerLength()```
- Player symbol assigned to you by game server can be obtained from ```gameConfiguration.getPlayerSymbol```

##### How it works
- At the top of the application there is ```GameFramework``` class which provides method ```.run(gameClient, ...args)``` for quick game client initialization.
This method needs to be called in ```Main``` class and expects two arguments ```gameClient::GameClient``` and ```args[]::String```.
First argument represents implementation of ```GameClient``` interface which must every player implement and provide its implementation to this method.
Second argument represents array of command-line arguments which is passed by game server and which must be provided into this method as well.

- Interface ```GameClient``` which must be implemented by player contains two methods:
  - ```public void init(GameConfiguration gameConfiguration)``` - this method is used by framework for game client initalization 
  by previously provided game configuration via commandline arguments
  - ```public Point getNextMove(PlayerSymbol[][] board)``` - this method is used for generating next move

- Implementation details for methods described above can be found in JavaDoc. 
- Sample, from scratch fully working implementation is included in this project.

##### How to test your solution
- For quick check, there is prepared JUnit 5 test suite which checks basic and advanced rules. 
- You can also add your own unit tests, so feel free to extend this suite.
- It is also possible to test your solution against NPC. Just run tests in ```SimpleSingleplayerTest``` class.

##### How to install dependencies
- For installation dependencies from JFrog artifactory, you have to obtain credentials for accounts specified in ```${PROJECT_ROOT}/settings.xml```.
- After that you can install dependencies via command:
  - ```mvn --settings settings.xml -Dmaven.wagon.http.ssl.insecure=true install``` 
- You can also download JavaDoc which is available in artifactory as well.

##### How to build application
- with dependencies:
```mvn clean compile assembly:single``` <- just for development
- without dependencies:
```mvn clean install -P prod``` <- for uploading solution into web application

##### Where is executable application build
- ```${PROJECT_ROOT}/target/tic-tac-toe-client-solution.jar```