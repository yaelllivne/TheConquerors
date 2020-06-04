package workOnFile;




import mypackage.GameDescriptor;
import mypackage.Teritory;
import mypackage.Unit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class CheckGameDescriptor {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "mypackage";

    static public String checkGameDescriptor(GameDescriptor gameDescriptor){
        if(!checkRows(gameDescriptor))
            return "number of rows isn't valid , number of rows must be 2-30";
        else if(!checkColumns(gameDescriptor))
            return "number of columns isn't valid , number of columns must be 3-30";
        else if(!checkInformationOnCell(gameDescriptor))
            return "there is no information on one cell or more";
        else if(!checkWrondIDCell(gameDescriptor))
            return "there is a cell with wrong ID";
        else if(!checkNumOfPlayers(gameDescriptor))
            return "number of players isn't valid , number of players must be 2-4";
       // else if(!checkDupPlayersID(gameDescriptor))
        //    return "there some players with the same ID";
        else if(!checkDupForceName(gameDescriptor))
            return "there some forces from the same type";
        else if(!checkDupForceRank(gameDescriptor))
            return "there some forces with the same rank";
        else if(!checkRanks(gameDescriptor))
            return "some units are missing";
        else return checkDoubleCell(gameDescriptor);
    }

    private static String checkDoubleCell(GameDescriptor gameDescriptor) {
        int length=gameDescriptor.getGame().getBoard().getRows().intValue()*gameDescriptor.getGame().getBoard().getColumns().intValue();
        int[] show = new int[length];
        for(int run:show){
            run=0;
        }
        for(Teritory run:gameDescriptor.getGame().getTerritories().getTeritory()) {
            if (run.getId().intValue() <= 0 || run.getId().intValue() > length)
                return "there is cell with wrong id";
            show[run.getId().intValue() - 1]++;
        }
        for(int run:show){
            if(run!=1&&run!=0) {
                return "there are two cell with the same id";
            }
        }
        return null;
    }

    private static boolean checkWrondIDCell(GameDescriptor gameDescriptor) {
        int length=gameDescriptor.getGame().getBoard().getRows().intValue()*gameDescriptor.getGame().getBoard().getColumns().intValue();
        for(Teritory run:gameDescriptor.getGame().getTerritories().getTeritory()){
            if(run.getId().intValue()<=0||run.getId().intValue()>=length)
                return false;
        }
        return true;
    }

    private static boolean checkInformationOnCell(GameDescriptor gameDescriptor) {
        if(gameDescriptor.getGame().getTerritories().getDefaultProfit()==null||gameDescriptor.getGame().getTerritories().getDefaultArmyThreshold()==null){
            if(gameDescriptor.getGame().getBoard().getColumns().intValue()*gameDescriptor.getGame().getBoard().getRows().intValue()!=gameDescriptor.getGame().getTerritories().getTeritory().size())
                return false;
        }
        return true;
    }

    private static boolean checkNumOfPlayers(GameDescriptor gameDescriptor) {
        int numPlayers = gameDescriptor.getDynamicPlayers().getTotalPlayers().intValue();
        return numPlayers > 1 && numPlayers < 5;
    }
/*
    private static boolean checkDupPlayersID(GameDescriptor gameDescriptor) {
        int numOfPlayers = gameDescriptor.getPlayers().getPlayer().size();
        for (int i = 0; i < numOfPlayers-1; i++) {
            for (int j = i + 1; j < numOfPlayers ; j++) {
                if(gameDescriptor.getPlayers().getPlayer().get(i).getId().intValue()==gameDescriptor.getPlayers().getPlayer().get(j).getId().intValue()){
                    return false;
                }
            }
        }
        return true;
    }
*/
    private static boolean checkDupForceName(GameDescriptor gameDescriptor) {
        int numOfForce = gameDescriptor.getGame().getArmy().getUnit().size();
        for (int i = 0; i < numOfForce-1; i++) {
            for (int j = i + 1; j < numOfForce; j++) {
                if(gameDescriptor.getGame().getArmy().getUnit().get(i).getType().compareTo(gameDescriptor.getGame().getArmy().getUnit().get(j).getType())==0){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkDupForceRank(GameDescriptor gameDescriptor) {
        int numOfForce = gameDescriptor.getGame().getArmy().getUnit().size();
        for (int i = 0; i < numOfForce-1; i++) {
            for (int j = i + 1; j < numOfForce ; j++) {
                if((int)gameDescriptor.getGame().getArmy().getUnit().get(i).getRank()==(int)gameDescriptor.getGame().getArmy().getUnit().get(j).getRank()){
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkRanks(GameDescriptor gameDescriptor) {
        int length = gameDescriptor.getGame().getArmy().getUnit().size();
        int[] show = new int[length];
        for (int run : show) {
            run = 0;
        }
        for (Unit run : gameDescriptor.getGame().getArmy().getUnit()) {
            int rank=run.getRank();
            if (rank <= length && rank > 0)
                show[rank-1]++;
        }
        for (int i = 0; i < length; i++)
            if (show[i] != 1)
                return false;
        return true;
    }

    private static boolean checkColumns(GameDescriptor gameDescriptor) {
        int numCols=gameDescriptor.getGame().getBoard().getColumns().intValue();
        return numCols>2&&numCols<31;
    }

    private static boolean checkRows(GameDescriptor gameDescriptor) {
        int numRows=gameDescriptor.getGame().getBoard().getRows().intValue();
        return numRows>1&&numRows<31;
    }
/*
    private GameDescriptor loadFile(String path) {
        File file = new File(path);
        //if (!file.exists()) {
        //    System.out.println("we don't found this file");
        //}
        try {
            return deserializeFrom(new FileInputStream(file));
        } catch (FileNotFoundException | JAXBException e) {
            System.out.println("we don't found this file");
            return null;
        }
    }
*/
    public static GameDescriptor deserializeFrom(String in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        StringReader sr = new StringReader(in);
        return (GameDescriptor) u.unmarshal(sr);
    }/*
    this.context = JAXBContext.newInstance("XmlComponents");
        this.unmarshaller = this.context.createUnmarshaller();
    StringReader sr = new StringReader(xmlDescription);
        this.gameDescriptor = (GameDescriptor)this.unmarshaller.unmarshal(sr);
        this.gameType = this.gameDescriptor.getGameType().toLowerCase();
----------------------------------------------------------------------------------------------------
    public String getGameDescriptor(String path) {
        GameDescriptor gameDescriptorCheck = loadFile(path);
        String massege=checkGameDescriptor(gameDescriptorCheck);
        //if(massege==null)
            //gameDescriptor=gameDescriptorCheck;
        return massege;
    }

 */
}
