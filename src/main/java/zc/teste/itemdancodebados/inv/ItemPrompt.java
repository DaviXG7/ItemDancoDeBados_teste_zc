package zc.teste.itemdancodebados.inv;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.enchantments.Enchantment;

@AllArgsConstructor
public class ItemPrompt implements Prompt {

    private PromptType promptType;
    private ItemState state;

    @Override
    public String getPromptText(ConversationContext conversationContext) {

        switch (promptType) {
            case NAME:
                return "§9Digite o nome do item:";
            case MATERIAL:
                return "§9Digite o material do item:";
            case LORE:
                return "§9Adicione uma linha à lore do item: (digite 'cancelar' para sair)";
            case AMOUNT:
                return "§9Digite a quantidade do item:";
            case DURABILITY:
                return "§9Digite a durabilidade do item:";
            case DATA:
                return "§9Digite a data do item:";
            case ENCHANTS:
                return "§9Digite os encantamentos do item: (formato: Encantamento, nível) (digite 'cancelar' para sair)";
            default:
                return "§c Error";
        }
    }

    @Override
    public boolean blocksForInput(ConversationContext conversationContext) {
        return true;
    }

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String s) {

        try {
            switch (promptType) {
                case NAME:
                    state.setName("§r" + s);
                    break;
                case MATERIAL:
                    state.setMaterial(Material.valueOf(s.toUpperCase()));
                    break;
                case LORE:
                    state.getLore().add("§r" + s);
                    return this;
                case AMOUNT:
                    state.setAmount(Integer.parseInt(s));
                    break;
                case DURABILITY:
                    state.setDurability(Short.parseShort(s));
                    break;
                case DATA:
                    state.setData(Byte.parseByte(s));
                    break;
                case ENCHANTS:
                    String[] splited = s.split(", ");
                    try {
                        state.getEnchants().put(Enchantment.getById(Integer.parseInt(splited[0])), Integer.parseInt(splited[1]));

                    } catch (Exception ignored) {}
                    state.getEnchants().put(Enchantment.getByName(splited[0].toUpperCase()), Integer.parseInt(splited[1]));
                    return this;
            }
        } catch (Exception e) {
            conversationContext.getForWhom().sendRawMessage("§cValor inválido.");
            e.printStackTrace();
            return this;
        }


        return END_OF_CONVERSATION;
    }


    public enum PromptType {
        NAME,
        MATERIAL,
        LORE,
        AMOUNT,
        DURABILITY,
        DATA,
        ENCHANTS
    }
}
