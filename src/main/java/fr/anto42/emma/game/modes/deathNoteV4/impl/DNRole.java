package fr.anto42.emma.game.modes.deathNoteV4.impl;


import fr.anto42.emma.coreManager.Module;
import fr.anto42.emma.coreManager.players.roles.Camp;
import fr.anto42.emma.coreManager.players.roles.Role;

public abstract class DNRole extends Role {
    public DNRole(String name, Camp camp, Module gamemode){
        super(name, camp, gamemode);
    }
    public void onEpisode(){
    }
}