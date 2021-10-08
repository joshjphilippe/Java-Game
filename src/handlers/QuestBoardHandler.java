package handlers;

/**
 * @author Joshua Jean-Philippe
 * This will handle all the quest boards within the game
 * There may even be a bounty system
 */
public class QuestBoardHandler {

    /**
     * Load all quests
     * Load specific quests into zone specific questboards
     * Example: allQuestsList
     * townA.add(allQuestsList.get(questIndex))
     * townB.add(allQuestsList.get(questIndex))
     * 
     * Quests have
     * Quest Title
     * Description
     * Rewards.. <-- Maybe a reward handler? Something similar to NPC drops.
     * Quest Master
     */

    private String questTitle;
    private String questDescription;
    private String questMaster;

    public QuestBoardHandler(String questTitle, String questDescription, String questMaster) {
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.questMaster = questMaster;
    }
    
}
