import java.util.*;
import java.util.function.*;
import java.io.*;

class HockeyPlayer extends FilterData {
	//fields
	private String lastName;
	private String position;
	private String birthplace;
	private String shoots;
	
	//constructor
	public HockeyPlayer(String lastName, String position, String birthplace, String shoots){
		this.lastName = lastName;
		this.position = position;
		this.birthplace = birthplace;
		this.shoots = shoots;
	}	
	
	//setters
	public void setLastName(String lastName){
		this.lastName = lastName;	
	}
	
	public void setPosition(String position){
		this.position = position;	
	}
	
	public void setBirthplace(String birthplace){
		this.birthplace = birthplace;	
	}
	
	public void setShoots(String shoots){
		this.shoots = shoots;	
	}
	
	//getters
	public String getLastName(){
		return lastName;
	}
	
	public String getPosition(){
		return position;	
	}
	
	public String getBirthplace(){
		return birthplace;	
	}
	
	public String getShoots(){
		return shoots;	
	}
	
	//more methods
	public String toString(){
		return lastName; 	
	}
	
	public String printGeneralInfo(){
	 	return lastName + " is a " + position + " who was born in " + this.getBirthplace() + ".";	
	}
	
	//implementations of non-static FilterData abstract class methods
	public boolean isPosition(String playerPosition){
		if(position.contains(playerPosition)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isShot(String leftRight){
		if(shoots.equals(leftRight)){
			return true;	
		}
		else{
			return false;	
		}
	}
	
	public boolean isBornHere(String country){
		if(birthplace.equals(country)){
			return true;		
		}
		else{
			return false;	
		}
	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
interface StatsTracking{
	public static final String[] teamOrIndiv = {"Team Info", "Individual Info"};
	public static final String[] timePeriods = {"2017 - 2018 Regular Season", "2016 - 2017 Regular Season", "2015 - 2016 Regular Season"};
	public static final String[] infoType = {"Roster Details", "Skaters' Stats Rankings", "Goalies' Stats Rankings"};
	
	public static final String[] teamDetails = {"Roster", "Players Grouped by Position", "Skaters Grouped by Shoots Direction", "Players Grouped by Country of Birth"};
	public static final String[] goalieStats = {"Shots Against", "Goals Against", "Saves", "Save Percentage"};
	public static final String[] skaterStats = {"Goals", "Assists", "Points", "+/-", "Shots", "Shooting Percentage"};	
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class GoalieStats{
	//fields
	private int shotsAgainst;
	private int goalsAgainst;
	private int saves;
	private double savePercentage;
	
	//constructor
	public GoalieStats(int goalsAgainst, int saves){
		this.goalsAgainst = goalsAgainst;
		this.saves = saves;
		setShotsAgainst();
		this.shotsAgainst = getShotsAgainst();
		setSavePercentage();
		this.savePercentage = getSavePercentage();
	}
	
	//setters
	public void setShotsAgainst(){
		shotsAgainst = goalsAgainst + saves;
	}
	
	public void setGoalsAgainst(int goalsAgainst){
		this.goalsAgainst = goalsAgainst;	
	}
	
	public void setSaves(int saves){
		this.saves = saves;
	}	
	
	public void setSavePercentage(){
		try{
			if(shotsAgainst > 0){
				savePercentage = ((double)saves/(double)shotsAgainst)*100;	
			}
			else{
				savePercentage = 0;	
			}
		}
		catch(ArithmeticException ae){
			System.out.println("Exception: " + ae + ".");	
		}
		catch(Exception e){
			System.out.println("Exception: " + e + ".");	
		}
	}
	
	//getters
	public int getShotsAgainst(){
		return shotsAgainst;	
	}
	
	public int getGoalsAgainst(){
		return goalsAgainst;	
	}
	
	public int getSaves(){
		return saves;	
	}
	
	public double getSavePercentage(){
		return savePercentage;	
	}
	
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Goalie extends HockeyPlayer implements Comparable<Goalie>{
	//fields
	private GoalieStats stats;
	private static int goalieStatsIndex = 3;

	//constructors
	public Goalie(String lastName, String position, String birthplace, String shoots, GoalieStats stats){
		super(lastName, position, birthplace, shoots);
		this.stats = stats;
	}
	
	public Goalie(String lastName, String position, String birthplace, String shoots, int goalsAgainst, int saves){
		super(lastName, position, birthplace, shoots);
		this.stats = new GoalieStats(goalsAgainst, saves);
	}
	
	public Goalie(HockeyPlayer hp, GoalieStats stats){
		this(hp.getLastName(), hp.getPosition(), hp.getBirthplace(), hp.getShoots(), stats);	
	}
	
	@Override
    	public int compareTo(Goalie other) {
    	switch(goalieStatsIndex){
	case 0:	if (this.getStats().getShotsAgainst() < other.getStats().getShotsAgainst()) {
			return -1;
		}
		else if (this.getStats().getShotsAgainst() == other.getStats().getShotsAgainst()) { 
			return 0;
		}
	break;
	case 1:	if (this.getStats().getGoalsAgainst() < other.getStats().getGoalsAgainst()) {
			return -1;
		}
		else if (this.getStats().getGoalsAgainst() == other.getStats().getGoalsAgainst()) { 
			return 0;
		}
	break;
	case 2:	if (this.getStats().getSaves() < other.getStats().getSaves()) {
			return -1;
		}
		else if (this.getStats().getSaves() == other.getStats().getSaves()) { 
			return 0;
		}
	break;
	case 3:	if (this.getStats().getSavePercentage() < other.getStats().getSavePercentage()) {
			return -1;
		}
		else if (this.getStats().getSavePercentage() == other.getStats().getSavePercentage()) { 
			return 0;
		}
	break;
	}
		return 1;
	}
	
	//setters
	public void setStats(GoalieStats stats){
		this.stats = stats;	
	}
	
	public void setGoalieStatsIndex(int index){
		goalieStatsIndex = index;	
	}
	
	//getters
	public GoalieStats getStats(){
		return stats;	
	}
	
	public int getGoalieStatsIndex(){
		return goalieStatsIndex;	
	}
	
	@Override
	public String toString(){
		return super.toString();
	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class SkaterStats{
	//fields
	private int goals;
	private int assists;
	private int points;
	private int plusMinus;
	private int shots;
	private double shootingPercentage;
	
	//constructor
	public SkaterStats(int goals, int assists, int plusMinus, int shots){
		this.goals = goals;
		this.assists = assists;
		this.plusMinus = plusMinus;
		this.shots = shots;
		setPoints();
		this.points = getPoints();
		setShootingPercentage();
		this.shootingPercentage = getShootingPercentage();
	}
	
	//setters
	public void setGoals(int goals){
		this.goals = goals;
	}
	
	public void setAssists(int assists){
		this.assists = assists;	
	}
	
	public void setPoints(){
		points = goals + assists;	
	}
	
	public void setPlusMinus(int plusMinus){
		this.plusMinus = plusMinus;	
	}
	
	public void setShots(int shots){
		this.shots = shots;	
	}
	
	public void setShootingPercentage(){
		try{
			if(shots > 0){
				shootingPercentage = ((double)goals/(double)shots)*100;	
			}
			else{
				shootingPercentage = 0;	
			}
		}
		catch(ArithmeticException ae){
			System.out.println("Exception: " + ae + ".");	
		}
		catch(Exception e){
			System.out.println("Exception: " + e + ".");	
		}
	}
	
	//getters
	public int getGoals(){
		return goals;	
	}
	
	public int getAssists(){
		return assists;	
	}
	
	public int getPoints(){
		return points;	
	}
	
	public int getPlusMinus(){
		return plusMinus;	
	}
	
	public int getShots(){
		return shots;	
	}
	
	public double getShootingPercentage(){
		return shootingPercentage;	
	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Skater extends HockeyPlayer implements Comparable<Skater>{
	//fields
	private SkaterStats stats;
	private static int skaterStatsIndex = 2;

	//constructors
	public Skater(String lastName, String position, String birthplace, String shoots, SkaterStats stats){
		super(lastName, position, birthplace, shoots);
		this.stats = stats;
	}
	
	public Skater(String lastName, String position, String birthplace, String shoots, int goals, int assists, int plusMinus, int shots){
		super(lastName, position, birthplace, shoots);
		this.stats = new SkaterStats(goals, assists, plusMinus, shots);
	}
	
	public Skater(HockeyPlayer hp, SkaterStats stats){
		this(hp.getLastName(), hp.getPosition(), hp.getBirthplace(), hp.getShoots(), stats);	
	}
	
	@Override
    	public int compareTo(Skater other) {
    	switch(skaterStatsIndex){
	case 0:	if (this.getStats().getGoals() < other.getStats().getGoals()) {
			return -1;
		}
		else if (this.getStats().getGoals() == other.getStats().getGoals()) { 
			return 0;
		}
	break;
	case 1:	if (this.getStats().getAssists() < other.getStats().getAssists()) {
			return -1;
		}
		else if (this.getStats().getAssists() == other.getStats().getAssists()) { 
			return 0;
		}
	break;
	case 2:	if (this.getStats().getPoints() < other.getStats().getPoints()) {
			return -1;
		}
		else if (this.getStats().getPoints() == other.getStats().getPoints()) { 
			return 0;
		}
	break;
	case 3:	if (this.getStats().getPlusMinus() < other.getStats().getPlusMinus()) {
			return -1;
		}
		else if (this.getStats().getPlusMinus() == other.getStats().getPlusMinus()) { 
			return 0;
		}
	break;
	case 4:	if (this.getStats().getShots() < other.getStats().getShots()) {
			return -1;
		}
		else if (this.getStats().getShots() == other.getStats().getShots()) { 
			return 0;
		}
	break;
	case 5:	if (this.getStats().getShootingPercentage() < other.getStats().getShootingPercentage()) {
			return -1;
		}
		else if (this.getStats().getShootingPercentage() == other.getStats().getShootingPercentage()) { 
			return 0;
		}
	break;
	}
		return 1;
	}
	
	//setters
	public void setStats(SkaterStats stats){
		this.stats = stats;
	}
	
	public void setSkaterStatsIndex(int index){
		skaterStatsIndex = index;
	}
	
	//getters
	public SkaterStats getStats(){
		return stats;	
	}
	
	public int getSkaterStatsIndex(){
		return skaterStatsIndex;	
	}
	
	@Override
	public String toString(){
		return super.toString();	
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
interface RosterData{
	//fields
	HockeyPlayer holtby = new HockeyPlayer("Holtby", "Goalie", "Canada", "N/A");
	GoalieStats holtbyStats2017 = new GoalieStats(153, 1495);
	GoalieStats holtbyStats2016 = new GoalieStats(127, 1563);
	GoalieStats holtbyStats2015 = new GoalieStats(141, 1661);
	
	HockeyPlayer grubauer = new HockeyPlayer("Grubauer", "Goalie", "Germany", "N/A");
	GoalieStats grubauerStats2017 = new GoalieStats(73, 880);
	GoalieStats grubauerStats2016 = new GoalieStats(43, 542);
	GoalieStats grubauerStats2015 = new GoalieStats(43, 480);
	
	HockeyPlayer test = new HockeyPlayer("Test", "Goalie", "Germany", "N/A");
	GoalieStats testStats2017 = new GoalieStats(730, 88);
	GoalieStats testStats2016 = new GoalieStats(100, 242);
	GoalieStats testStats2015 = new GoalieStats(45, 400);
	
	HockeyPlayer ovechkin = new HockeyPlayer("Ovechkin", "Forward, LW", "Russia", "Right");
	SkaterStats ovechkinStats2017 = new SkaterStats(49, 38, 3, 355);
	SkaterStats ovechkinStats2016 = new SkaterStats(33, 36, 6, 313);
	SkaterStats ovechkinStats2015 = new SkaterStats(50, 21, 21, 398);
	
	HockeyPlayer kuznetsov = new HockeyPlayer("Kuznetsov", "Forward, C", "Russia", "Left");
	SkaterStats kuznetsovStats2017 = new SkaterStats(27, 56, 3, 187);
	SkaterStats kuznetsovStats2016 = new SkaterStats(19, 40, 18, 170);
	SkaterStats kuznetsovStats2015 = new SkaterStats(20, 57, 27, 193);
	
	HockeyPlayer backstrom = new HockeyPlayer("Backstrom", "Forward, C", "Sweden", "Left");
	SkaterStats backstromStats2017 = new SkaterStats(21, 50, 5, 165);
	SkaterStats backstromStats2016 = new SkaterStats(23, 63, 17, 162);
	SkaterStats backstromStats2015 = new SkaterStats(20, 50, 17, 129);
	
	HockeyPlayer vrana = new HockeyPlayer("Vrana", "Forward, LW", "Czech Republic", "Left");
	SkaterStats vranaStats2017 = new SkaterStats(13, 14, 2, 133);
	
	HockeyPlayer gersich = new HockeyPlayer("Gersich", "Forward, LW", "USA", "Left");
	SkaterStats gersichStats2017 = new SkaterStats(0, 1, -1, 4);
	
	HockeyPlayer walker = new HockeyPlayer("Walker", "Forward, LW", "Wales", "Left");
	SkaterStats walkerStats2017 = new SkaterStats(1, 0, 1, 4);
	
	HockeyPlayer burakovsky = new HockeyPlayer("Burakovsky", "Forward, LW", "Austria", "Left");
	SkaterStats burakovskyStats2017 = new SkaterStats(12, 13, 3, 84);
	
	HockeyPlayer graovac = new HockeyPlayer("Graovac", "Forward, C", "Canada", "Left");
	SkaterStats graovacStats2017 = new SkaterStats(0, 0, -3, 1);
	
	HockeyPlayer boyd = new HockeyPlayer("Boyd", "Forward, C", "USA", "Right");
	SkaterStats boydStats2017 = new SkaterStats(0, 1, 2, 2);
	
	HockeyPlayer obrien = new HockeyPlayer("O'Brien", "Forward, C", "Canada", "Left");
	SkaterStats obrienStats2017 = new SkaterStats(0, 0, 0, 1);
	
	HockeyPlayer eller = new HockeyPlayer("Eller", "Forward, C", "Denmark", "Left");
	SkaterStats ellerStats2017 = new SkaterStats(18, 20, -6, 161);
	
	HockeyPlayer stephenson = new HockeyPlayer("Stephenson", "Forward, C", "Canada", "Left");
	SkaterStats stephensonStats2017 = new SkaterStats(6, 12, 13, 36);
	
	HockeyPlayer beagle = new HockeyPlayer("Beagle", "Forward, C", "Canada", "Right");
	SkaterStats beagleStats2017 = new SkaterStats(7, 15, 3, 65);
	
	HockeyPlayer oshie = new HockeyPlayer("Oshie", "Forward, RW", "USA", "Right");
	SkaterStats oshieStats2017 = new SkaterStats(18, 29, 2, 127);
	
	HockeyPlayer wilson = new HockeyPlayer("Wilson", "Forward, RW", "Canada", "Right");
	SkaterStats wilsonStats2017 = new SkaterStats(14, 21, 10, 123);
	
	HockeyPlayer connolly = new HockeyPlayer("Connolly", "Forward, RW", "Canada", "Right");
	SkaterStats connollyStats2017 = new SkaterStats(15, 12, -6, 67);
	
	HockeyPlayer peluso = new HockeyPlayer("Peluso", "Forward, RW", "Canada", "Right");
	SkaterStats pelusoStats2017 = new SkaterStats(0, 0, 0, 0);
	
	HockeyPlayer smithPelly = new HockeyPlayer("Smith-Pelly", "Forward, RW", "Canada", "Right");
	SkaterStats smithPellyStats2017 = new SkaterStats(7, 9, -6, 103);
	
	HockeyPlayer chiasson = new HockeyPlayer("Chiasson", "Forward, RW", "Canada", "Right");
	SkaterStats chiassonStats2017 = new SkaterStats(9, 9, 1, 59);
	
	HockeyPlayer carlson = new HockeyPlayer("Carlson", "Defense", "USA", "Right");
	SkaterStats carlsonStats2017 = new SkaterStats(15, 53, 0, 237);
	
	HockeyPlayer orlov = new HockeyPlayer("Orlov", "Defense", "Russia", "Left");
	SkaterStats orlovStats2017 = new SkaterStats(10, 21, 10, 125);
	
	HockeyPlayer niskanen = new HockeyPlayer("Niskanen", "Defense", "USA", "Right");
	SkaterStats niskanenStats2017 = new SkaterStats(7, 22, 24, 120);
	
	HockeyPlayer djoos = new HockeyPlayer("Djoos", "Defense", "Sweden", "Left");
	SkaterStats djoosStats2017 = new SkaterStats(3, 11, 13, 60);
	
	HockeyPlayer bowey = new HockeyPlayer("Bowey", "Defense", "Canada", "Right");
	SkaterStats boweyStats2017 = new SkaterStats(0, 12, -3, 47);
	
	HockeyPlayer orpik = new HockeyPlayer("Orpik", "Defense", "USA", "Left");
	SkaterStats orpikStats2017 = new SkaterStats(0, 10, -9, 54);
	
	HockeyPlayer chorney = new HockeyPlayer("Chorney", "Defense", "Canada", "Left");
	SkaterStats chorneyStats2017 = new SkaterStats(1, 3, 8, 14);
	
	HockeyPlayer jerabek = new HockeyPlayer("Jerabek", "Defense", "Czech Republic", "Left");
	SkaterStats jerabekStats2017 = new SkaterStats(1, 3, -1, 11);
	
	HockeyPlayer kempny = new HockeyPlayer("Kempny", "Defense", "Czech Republic", "Left");
	SkaterStats kempnyStats2017 = new SkaterStats(2, 1, 1, 32);
	
	HockeyPlayer ness = new HockeyPlayer("Ness", "Defense", "USA", "Left");
	SkaterStats nessStats2017 = new SkaterStats(0, 1, 2, 2);

	public static ArrayList<HockeyPlayer> roster2017(){
		ArrayList<HockeyPlayer> roster2017 = new ArrayList<HockeyPlayer>();
		roster2017.add(new Goalie(holtby, holtbyStats2017));
		roster2017.add(new Skater(ovechkin, ovechkinStats2017));
		roster2017.add(new Skater(kuznetsov, kuznetsovStats2017));
		roster2017.add(new Skater(vrana, vranaStats2017));
		roster2017.add(new Skater(gersich, gersichStats2017));
		roster2017.add(new Skater(walker, walkerStats2017));
		roster2017.add(new Skater(burakovsky, burakovskyStats2017));
		roster2017.add(new Skater(backstrom, backstromStats2017));
		roster2017.add(new Skater(graovac, graovacStats2017));
		roster2017.add(new Skater(boyd, boydStats2017));
		roster2017.add(new Skater(obrien, obrienStats2017));
		roster2017.add(new Skater(eller, ellerStats2017));
		roster2017.add(new Skater(stephenson, stephensonStats2017));
		roster2017.add(new Skater(beagle, beagleStats2017));
		roster2017.add(new Skater(oshie, oshieStats2017));
		roster2017.add(new Skater(wilson, wilsonStats2017));
		roster2017.add(new Skater(connolly, connollyStats2017));
		roster2017.add(new Skater(peluso, pelusoStats2017));
		roster2017.add(new Skater(smithPelly, smithPellyStats2017));
		roster2017.add(new Skater(chiasson, chiassonStats2017));
		roster2017.add(new Skater(carlson, carlsonStats2017));
		roster2017.add(new Skater(orlov, orlovStats2017));
		roster2017.add(new Skater(niskanen, niskanenStats2017));
		roster2017.add(new Skater(djoos, djoosStats2017));
		roster2017.add(new Skater(bowey, boweyStats2017));
		roster2017.add(new Skater(orpik, orpikStats2017));
		roster2017.add(new Skater(chorney, chorneyStats2017));
		roster2017.add(new Skater(jerabek, jerabekStats2017));
		roster2017.add(new Skater(kempny, kempnyStats2017));
		roster2017.add(new Skater(ness, nessStats2017));
		roster2017.add(new Goalie(grubauer, grubauerStats2017));	
		roster2017.add(new Goalie(test, testStats2017));	
		return roster2017;
	}
	
	public static ArrayList<HockeyPlayer> roster2016(){
		ArrayList<HockeyPlayer> roster2016 = new ArrayList<HockeyPlayer>();
		roster2016.add(new Goalie(holtby, holtbyStats2016));
		roster2016.add(new Goalie(grubauer, grubauerStats2016));
		roster2016.add(new Goalie(test, testStats2016));
		roster2016.add(new Skater(ovechkin, ovechkinStats2016));
		roster2016.add(new Skater(kuznetsov, kuznetsovStats2016));
		roster2016.add(new Skater(backstrom, backstromStats2016));
		return roster2016;
	}
	
	public static ArrayList<HockeyPlayer> roster2015(){
		ArrayList<HockeyPlayer> roster2015 = new ArrayList<HockeyPlayer>();
		roster2015.add(new Goalie(holtby, holtbyStats2015));
		roster2015.add(new Goalie(grubauer, grubauerStats2015));
		roster2015.add(new Goalie(test, testStats2015));
		roster2015.add(new Skater(ovechkin, ovechkinStats2015));
		roster2015.add(new Skater(kuznetsov, kuznetsovStats2015));
		roster2015.add(new Skater(backstrom, backstromStats2015));
		return roster2015;
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Roster{
	//fields
	private ArrayList<HockeyPlayer> roster;

	//constructor
	public Roster(ArrayList<HockeyPlayer> seasonRoster){
		setRoster(seasonRoster);
	}
	
	//setter
	public void setRoster(ArrayList<HockeyPlayer> seasonRoster){
		roster = new ArrayList<HockeyPlayer>(seasonRoster);	
	}
	
	//getter
	public ArrayList<HockeyPlayer> getRoster(){
		return roster;	
	}
	
	//method to print roster by position w/out lambda expressions
	public void printRoster(ArrayList<HockeyPlayer> roster){
		String[] positions = {"Forward, LW", "Forward, C", "Forward, RW", "Defense", "Goalie"};
		System.out.println("Roster:\n");
		try{
			for(String position : positions){
				ArrayList<String> metCondition = new ArrayList<String>();
				for(HockeyPlayer h : roster){
					if(h.isPosition(position))
						metCondition.add(h.getLastName());	
				}
				Collections.sort(metCondition);
				for(String player : metCondition){
					System.out.println("POSITION: " + position+ "\tNAME: " + player);
				}
		}
		}
		catch(NullPointerException np){
			System.out.println("Null Pointer Exception: " + np);	
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}	
	}
	
	//method to print bios of players in roster
	public void playerBios(ArrayList<HockeyPlayer> roster){	
		System.out.println("**********************************************************************");
		System.out.println("\n**********************************************************************");
		System.out.println("Player Bios\n");
		for(HockeyPlayer o : roster){
			System.out.println(o.printGeneralInfo());	
		}
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
abstract class FilterData{
	public abstract boolean isPosition(String playerPosition);
	public abstract boolean isShot(String leftRight);
	public abstract boolean isBornHere(String country);
	
	//print method
	public static void print(ArrayList<HockeyPlayer> roster, Predicate<HockeyPlayer> predicate){
		try{
			ArrayList<String> meetConditions = new ArrayList<String>();
			for(HockeyPlayer player : roster){
				if(predicate.test(player)){
					meetConditions.add(player.getLastName());
				}
			}
			Collections.sort(meetConditions);
			for(String player : meetConditions){
				System.out.print(player + ", ");	
			}
			System.out.println();
		}
		catch(NullPointerException np){
			System.out.println("Null Pointer Exception: " + np);	
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}
	}
	
	//method to filter roster by position
	public static void printLambdaRoster(ArrayList<HockeyPlayer> roster){
		System.out.println("Players Grouped by Position: ");
		try{
			//this looping structure retrieves available positions from season's roster
			ArrayList<String> positions = new ArrayList<String>();
			positions.add(roster.get(0).getPosition());
			for(int i=1; i< roster.size(); i++){
				if(positions.contains(roster.get(i).getPosition() )== false){
					positions.add(roster.get(i).getPosition());	
				}
			}
			for(String position : positions){
				System.out.print(position.toUpperCase() + ": ");
				print (roster, h -> h.isPosition(position));
			}
		}
		catch(NullPointerException np){
			System.out.println("Null Pointer Exception: " + np);	
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}
	}
	
	//method to filter players by country of birth
	public static void printLambdaBirthplaces(ArrayList<HockeyPlayer> roster){
		System.out.println("Players Grouped by Country of Birth: ");
		try{
			//this looping structure retrieves available birthplaces from season's roster
			ArrayList<String> bp = new ArrayList<String>();
			bp.add(roster.get(0).getBirthplace());
			for(int i=1; i< roster.size(); i++){
				if(bp.contains(roster.get(i).getBirthplace() )== false){
					bp.add(roster.get(i).getBirthplace());	
				}
			}
			for(String country: bp){
				System.out.print("BORN IN " + country.toUpperCase() + ":  ");
				print (roster, h -> h.isBornHere(country));
			}
		}
		catch(NullPointerException np){
			System.out.println("Null Pointer Exception: " + np);	
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}
	}
	
	//method to filter skaters who shoot left/right
	public static void printLambdaShoots(ArrayList<HockeyPlayer> roster){
		System.out.println("Skaters Grouped by Shoots L/R: ");
		try{
			//this looping structure retrieves available shooting options from season's roster
			ArrayList<String> shoots = new ArrayList<String>();
			shoots.add(roster.get(0).getShoots());
			for(int i=1; i< roster.size(); i++){
				if(shoots.contains(roster.get(i).getShoots() )== false){
					shoots.add(roster.get(i).getShoots());	
				}
			}
			for(String shot : shoots){
				System.out.print("SHOOTS " + shot.toUpperCase() +":  ");
				print(roster, h -> h.isShot(shot));
			}
		}
		catch(NullPointerException np){
			System.out.println("Null Pointer Exception: " + np);	
		}
		catch(Exception e){
			System.out.println("Exception: " + e);	
		}
	}
	
	//method to filter skater-specific stats
	public static void printSkaterStat(String[] statOptions, int indexOfStat, ArrayList<HockeyPlayer> roster){
		System.out.println("Skater's " + statOptions[indexOfStat] + ": ");
		ArrayList<Skater> unsortedStat = new ArrayList<Skater>();
			for(HockeyPlayer player : roster){
				if(player.isPosition("Defense") || player.isPosition("Forward")){
					Skater skater = (Skater)player;
					skater.setSkaterStatsIndex(indexOfStat);
					unsortedStat.add(skater);
				}
			}
			Collections.sort(unsortedStat);
			for(Skater s : unsortedStat){
				switch(indexOfStat){
					case 0: 
						System.out.println(s + "'s " + statOptions[indexOfStat] + ": " + s.getStats().getGoals()); 
						break;
					case 1: System.out.println(s + "'s " + statOptions[indexOfStat] + ": " + s.getStats().getAssists()); 
						break;
					case 2: System.out.println(s + "'s " + statOptions[indexOfStat] + ": " + s.getStats().getPoints());
						break;
					case 3: System.out.println(s + "'s " + statOptions[indexOfStat] + ": " + s.getStats().getPlusMinus());
						break;
					case 4: System.out.println(s + "'s " + statOptions[indexOfStat] + ": " + s.getStats().getShots());
						break;
					case 5: System.out.println(s + "'s " + statOptions[indexOfStat] + ": " + s.getStats().getShootingPercentage()); 
						break;
				}
			}
	}
	
	//method to filter goalie-specific stats
	public static void printGoalieStat(String[] statOptions, int indexOfStat, ArrayList<HockeyPlayer> roster){
		System.out.println("Goalie's " + statOptions[indexOfStat] + ": ");
		ArrayList<Goalie> unsortedStat = new ArrayList<Goalie>();
			for(HockeyPlayer player : roster){
				if(player.isPosition("Goalie")){
					Goalie goalie = (Goalie)player;
					goalie.setGoalieStatsIndex(indexOfStat);
					unsortedStat.add(goalie);
				}
			}
			Collections.sort(unsortedStat);
			for(Goalie g : unsortedStat){
				switch(indexOfStat){
					case 0: System.out.println(g + "'s " + statOptions[indexOfStat] + ": " + g.getStats().getShotsAgainst()); 
						break;
					case 1: System.out.println(g + "'s " + statOptions[indexOfStat] + ": " + g.getStats().getGoalsAgainst());
						break;
					case 2: System.out.println(g + "'s " + statOptions[indexOfStat] + ": " + g.getStats().getSaves()); 
						break;
					case 3: System.out.println(g + "'s " + statOptions[indexOfStat] + ": " + g.getStats().getSavePercentage() + " -OR- " + g.getStats().getSavePercentage()/100.00 + " (the latter because this stat sometimes reported like this...)"); 
						break;
				}
			}
	}
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Output implements StatsTracking{
	//fields
	private BufferedReader reader;
	private static ArrayList<HockeyPlayer> seasonRoster;
	private static String season;
	
	//constructor
	public Output(){
		setReader();
	}
	
	//setter
	public void setReader(){
		reader = new BufferedReader(new InputStreamReader(System.in));		
	}
	
	public void setSeasonRoster(ArrayList<HockeyPlayer> seasonRoster){
		this.seasonRoster	= seasonRoster;
	}
	
	public void setSeason(String season){
		this.season = season;
	}	
	
	//getter
	public BufferedReader getReader(){
		return reader;
	}
	
	public ArrayList<HockeyPlayer> getSeasonRoster(){
		return seasonRoster;	
	}
	
	public String getSeason(){
		return season;
	}
	
	//more methods
	public int userChoice(){
		int userChoice = 0;
		try{
			System.out.print("Enter selection: ");
			userChoice = Integer.parseInt(reader.readLine());	
			System.out.println("********************************************************************");
		}
		catch(NumberFormatException nfe){
			System.out.println("NumberFormatException in userChoice method: " + nfe + ".");	
		}
		catch(IOException ie){
			System.out.println("IOException in userChoice method: " + ie + ".");	
		}
		catch(Exception e){
			System.out.println("Exception in userChoice method: " + e + ".");
		}
		return userChoice;
	}
	
	//method to output available menu options
	public void menuOptions(String[] opts, boolean timeMenu){
		System.out.println("\nMake a selection:");
		for(int i = 0; i < opts.length; i++){
			System.out.println("\t" + (i+1) + ".) " + opts[i]);	
		}
		if(timeMenu == true){
			System.out.println("\n\t" + (opts.length + 1) + ".) Return to Era Selection Menu");
			System.out.println("\t" + (opts.length + 2) + ".) Return to Team or Individual Selection Menu");
			System.out.println("\n\t" + (opts.length + 3) + ".)  EXIT\n");	
		}
		else{
			System.out.println("\n\t" + (opts.length + 1) + ".)  EXIT\n");	
		}
	}
	
	public void selectTeamOrIndivMenu(){
		System.out.println("\n********************************************************************");
		System.out.println("WELCOME TO HOCKEY DATA ANALYTICS!");
		menuOptions(StatsTracking.teamOrIndiv, false);
		int choice = userChoice();
		switch(choice){
		case 1: selectYearMenu();
			break;
		case 2: System.out.println("IN PROGRESS");
			break;
		case 3: System.out.print("  You selected: EXIT");
			System.exit(0);
			break;
		}
		selectTeamOrIndivMenu();
	}
	
	public void selectYearMenu(){
		System.out.println("\n********************************************************************");
		System.out.println("WELCOME TO TIME PERIOD SELECTION FOR HOCKEY DATA ANALYTICS!");
		menuOptions(StatsTracking.timePeriods, false);
		int choice = userChoice();
		switch(choice){
		case 1: setSeasonRoster(RosterData.roster2017());
			setSeason(StatsTracking.timePeriods[0]);
			break;
		case 2: setSeasonRoster(RosterData.roster2016());
			setSeason(StatsTracking.timePeriods[1]);
			break;
		case 3: setSeasonRoster(RosterData.roster2015());
			setSeason(StatsTracking.timePeriods[2]);
			break;
		case 4: System.out.print("  You selected: EXIT");
			System.exit(0);
			break;
		}
		infoTypeMenu();
	}
	
	public void infoTypeMenu(){
		Roster r = new Roster(getSeasonRoster());
		System.out.println("\n********************************************************************");
		System.out.println("WELCOME TO HOCKEY DATA ANALYTICS for the " + getSeason() + "!");
		menuOptions(StatsTracking.infoType, true);
		int choice = userChoice();
		switch(choice){
		case 1: rosterDetailsMenu();
			break;
		case 2: skatersStatsMenu();
			break;
		case 3: goaliesStatsMenu();
			break;
		case 4: selectYearMenu();
			break;
		case 5: selectTeamOrIndivMenu();
			break;
		case 6: System.out.print("  You selected: EXIT");
			System.exit(0);
			break;
		}
	}
	
	public void rosterDetailsMenu(){
		Roster r = new Roster(getSeasonRoster());
		System.out.println("\n********************************************************************");
		System.out.println("WELCOME TO ROSTER DETAILS for the " + getSeason() + "!");
		menuOptions(StatsTracking.teamDetails, true);
		int choice = userChoice();
		switch(choice){
		case 1: r.printRoster(r.getRoster());
			break;
		case 2: FilterData.printLambdaRoster(r.getRoster());
			break;
		case 3: FilterData.printLambdaShoots(r.getRoster());
			break;
		case 4: FilterData.printLambdaBirthplaces(r.getRoster());
			break;
		case 5: selectYearMenu();
			break;
		case 6: selectTeamOrIndivMenu();
			break;
		case 7: System.out.print("  You selected: EXIT");
			System.exit(0);
			break;
		}
		rosterDetailsMenu();
	}
	
	public void skatersStatsMenu(){
		Roster r = new Roster(getSeasonRoster());
		System.out.println("\n********************************************************************");
		System.out.println("WELCOME TO SKATER DATA ANALYTICS for the " + getSeason() + "!");
		menuOptions(StatsTracking.skaterStats, true);
		int choice = userChoice();
		switch(choice){
		case 1: FilterData.printSkaterStat(StatsTracking.skaterStats, 0, r.getRoster());
			break;
		case 2: FilterData.printSkaterStat(StatsTracking.skaterStats, 1, r.getRoster());
			break;
		case 3: FilterData.printSkaterStat(StatsTracking.skaterStats, 2, r.getRoster());
			break;
		case 4: FilterData.printSkaterStat(StatsTracking.skaterStats, 3, r.getRoster());
			break;
		case 5: FilterData.printSkaterStat(StatsTracking.skaterStats, 4, r.getRoster());
			break;
		case 6: FilterData.printSkaterStat(StatsTracking.skaterStats, 5, r.getRoster());
			break;
		case 7: selectYearMenu();
			break;
		case 8: selectTeamOrIndivMenu();
			break;
		case 9: System.out.print("  You selected: EXIT");
			System.exit(0);
			break;
		}
		skatersStatsMenu();
	}
	
	public void goaliesStatsMenu(){
		Roster r = new Roster(getSeasonRoster());
		System.out.println("\n********************************************************************");
		System.out.println("WELCOME TO GOALIE DATA ANALYTICS for the " + getSeason() + "!");
		menuOptions(StatsTracking.goalieStats, true);
		int choice = userChoice();
		switch(choice){
		case 1: FilterData.printGoalieStat(StatsTracking.goalieStats, 0, r.getRoster());
			break;
		case 2: FilterData.printGoalieStat(StatsTracking.goalieStats, 1, r.getRoster());
			break;
		case 3: FilterData.printGoalieStat(StatsTracking.goalieStats, 2, r.getRoster());
			break;
		case 4: FilterData.printGoalieStat(StatsTracking.goalieStats, 3, r.getRoster());
			break;
		case 5: selectYearMenu();
			break;
		case 6: selectTeamOrIndivMenu();
			break;
		case 7: System.out.print("  You selected: EXIT");
			System.exit(0);
			break;
		}
		goaliesStatsMenu();
	}
	
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class SortingRevisitedV2{
	public static void main(String... args){		
		Output output = new Output();
		output.selectTeamOrIndivMenu();
	}
}