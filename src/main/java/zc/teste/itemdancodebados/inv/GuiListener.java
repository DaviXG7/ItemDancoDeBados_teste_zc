package zc.teste.itemdancodebados.inv;

import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import zc.teste.itemdancodebados.Item;
import zc.teste.itemdancodebados.ItemDancoDeBados;

public class GuiListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (! (event.getInventory().getHolder() instanceof ItemCreatorGui)) return;

        event.setCancelled(true);

        ItemCreatorGui gui = (ItemCreatorGui) event.getInventory().getHolder();

        Player player = (Player) event.getWhoClicked();

        switch (event.getSlot()) {
            case 28:

                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.NAME, gui.getState(), gui).begin();

                break;
            case 29:

                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.LORE, gui.getState(), gui).begin();

                break;
            case 30:
                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.AMOUNT, gui.getState(), gui).begin();

                break;
            case 31:
                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.MATERIAL, gui.getState(), gui).begin();

                break;
            case 32:
                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.DURABILITY, gui.getState(), gui).begin();

                break;
            case 33:
                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.DATA, gui.getState(), gui).begin();

                break;
            case 34:
                player.closeInventory();

                buildConversation(player, ItemPrompt.PromptType.ENCHANTS, gui.getState(), gui).begin();

                break;
            case 49:

                player.sendMessage("§eSalvando item....");
                ItemDancoDeBados.getInstance().getDatabaseManager().getItemDAO()
                        .saveItem(gui.getItem()).thenRun(() -> {
                            player.sendMessage("§aItem salvo com sucesso!");
                            player.getInventory().addItem(gui.getItem().getItem());
                        });

                break;
        }
    }
    private Conversation buildConversation(Player player, ItemPrompt.PromptType type, ItemState state, ItemCreatorGui gui) {
        return new ConversationFactory(ItemDancoDeBados.getInstance())
                .withEscapeSequence("cancelar")
                .withFirstPrompt(new ItemPrompt(type, state))
                .addConversationAbandonedListener(e -> {
                    gui.update();
                    player.openInventory(gui.getInventory());
                })
                .buildConversation(player);
    }


}
