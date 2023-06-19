import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQL {
	public String select(String searchItem,String itemName, String item,String table) {
		return String.format("SELECT %s FROM %s where %s = '%s'",searchItem,table,itemName,item);
	}
	
	public String select(String searchItem,String itemName, int item,String table) {
		return String.format("SELECT %s FROM %s where %s = %d",searchItem,table,itemName,item);
	}
	
	public String selectByTwo(String searchItem,String itemName1, int item1,String itemName2, String item2,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = '%s'",searchItem,table,itemName1,item1,itemName2,item2);
	}
	
	public String selectByTwo(String searchItem,String itemName1, int item1,String itemName2, int item2,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = %s",searchItem,table,itemName1,item1,itemName2,item2);
	}
	
	public String selectByTwoOrder(String searchItem,String itemName1, int item1,String itemName2, int item2,String orderName,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = %s ORDER BY %s",searchItem,table,itemName1,item1,itemName2,item2,orderName);
	}
	
	public String selectByTwoOrder(String searchItem,String itemName1, int item1,String itemName2, String item2,String orderName,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = '%s' ORDER BY %s",searchItem,table,itemName1,item1,itemName2,item2,orderName);
	}
	
	public String ifExist(String itemName, String item, String table) {
		return String.format("SELECT 1 FROM %s WHERE %s = '%s' LIMIT 1",table,itemName,item);
	}
	
	public String ifExist(String itemName, int item, String table) {
		return String.format("SELECT 1 FROM %s WHERE %s = %s LIMIT 1",table,itemName,item);
	}
	
	public String ifExistByTwo(String itemName1, int item1, String itemName2, String item2,String table) {
		return String.format("SELECT 1 FROM %s WHERE %s = %s AND %s = '%s' LIMIT 1",table,itemName1,item1,itemName2,item2);
	}
	
	public String insertUser(String name,String password,int id) {
		return String.format("INSERT INTO User (Name,ID,Password) VALUES ('%s',%d,'%s')",name,id,password);
	}
	
	public String insertSemester(String name, String beginDate, String endDate, String introduction,int id) {
		return String.format("INSERT INTO Semester(Name, BeginDate, EndDate, Introduction, UserID) VALUES ('%s','%s','%s','%s',%d)",name,beginDate,endDate,introduction,id);
	}
	
	public String updateSemester(String name, String beginDate, String endDate, String introduction,int semesterID) {
		return String.format("UPDATE Semester SET Name='%s', BeginDate='%s',EndDate='%s',Introduction='%s' WHERE SemesterID = %d",name,beginDate,endDate,introduction,semesterID);
	}
	
	public String insertSchedule(String name,String introduction, int beginTime, int endTime, String dayOfWeek, String location,int semesterid) {
		return String.format("INSERT INTO Schedule(Name, Introduction, SemesterID, DayOfWeek, Location, BeginTime, EndTime) VALUES ('%s','%s',%s,%s,'%s',%s,%s)",name,introduction,semesterid,dayOfWeek,location,beginTime,endTime);
	}
	
	public String updateSchedule(String name,String introduction, int beginTime, int endTime, String dayOfWeek, String location,int scheduleid) {
		return String.format("UPDATE Schedule SET Name='%s',Introduction='%s',DayOfWeek= %s ,Location='%s',BeginTime=%s,EndTime=%s WHERE ScheduleID = %s",name,introduction,dayOfWeek,location,beginTime,endTime,scheduleid);
	}
	
	public String insertTask(String name,String introduction, String beginDate, String endDate, String cTime, String bTime,String location,int scheduleID, int semesterID,int timer) {
		return String.format("INSERT INTO Task(Name, Introduction, ScheduleID, BeginDate, EndDate, Timer, isFinished, ConcentrateTime, BreakTime, SemesterID, Location) VALUES('%s','%s',%s,'%s','%s',%s,0,'%s','%s',%s,'%s')", name,introduction,scheduleID,beginDate,endDate,timer,cTime,bTime,semesterID,location);
	}
	
	public String updateTask(String name,String introduction, String beginDate, String endDate, String cTime, String bTime,String location,int scheduleID, int taskID,int timer) {
		return String.format("UPDATE Task SET Name='%s',Introduction='%s',ScheduleID='%s',BeginDate='%s',EndDate='%s',Timer=%s,isFinished=0,ConcentrateTime='%s',BreakTime='%s',Location='%s' WHERE TaskID=%s", name,introduction,scheduleID,beginDate,endDate,timer,cTime,bTime,location,taskID);
	}
	
	public String updateTaskFinished(int taskID) {
		return String.format("UPDATE Task SET isFinished=1 WHERE TaskID=%s", taskID);
	}
	
	public String delete(String itemName,int item,String table) {
		return String.format("DELETE FROM %s WHERE %s = %s",table,itemName,item);
	}
	
	public static String showResultSet(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String output = "";
		while (result.next()) {
		for (int i = 1; i <= columnCount; i++) {
			output += String.format(result.getString(i));
		}
		}
		return output;
	}
	
	public static String showResultSetMuitiple(ResultSet result) throws SQLException {
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		String output = "";
		while (result.next()) {
		for (int i = 1; i <= columnCount; i++) {
			output += String.format(result.getString(i));
		}
			output+=",";
		}
		return output;
	}
}