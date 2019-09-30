package fun.pearce.mobcoins.struct;

public enum Permission {

    CMD_CHECK_BALANCE("checkbal"),
    CMD_GIVE_BALANCE("givecoins"),
    CMD_SET_BALANCE("setcoins"),
    CMD_REMOVE_BALANCE("removecoins"),
    CMD_WITHDRAW_BALANCE("withdrawcoins");

    /**
     * The permission node to check for
     */
    private String node;

    /**
     * Takes the node and completes it
     *
     * @param node
     *      The node to concatenate
     */
    Permission(String node) {
        this.node = "mobcoins." + node;
    }

    /**
     * Get the permission node for checking if the player has it
     *
     * @return
     *      The permission node
     */
    public String getPermissionNode() {
        return this.node;
    }

}
