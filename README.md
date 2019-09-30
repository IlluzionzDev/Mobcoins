# Mobcoins

Mobcoins is a lightweight plugin built on Spigot 1.8.8. It adds a new element to the game of killing mobs to get **mobcoins**. These mobcoins can then be stored in a balance or traded with other players. They then can be used in a special shop to get their hands on more items.

## Index
* [Dependencies](#dependencies)
* [Features](#features)
* [Getting the plugin running](#getting-the-plugin-running)
* [Usage](#usage)
* [Permissions](#permissions)
* [Configuring the plugin](#configuring-the-plugin)
* [Contributing to this project](#contributing-to-this-project)
* [Contributors](#contributors-to-this-project)
* [Status](#status)

## Dependencies
* [Spigot API 1.8.8](https://hub.spigotmc.org/jenkins/job/BuildTools/) | This is the server jar. Download from buildtools if you don't have it already

## Features

* Customize any mob to drop coins
* Edit balances with commands
* Customize the shop with any items
* Physical tokens to withdraw and redeem

## Getting the plugin running
* First, download the `.jar` file from the releases tab.
* Stop the server if it's already running.
* Drop the downloaded `.jar` into the `/plugins` folder of the server.
* Now start the server and make sure no errors appear.
* The plugin should now be in the server up and running.

## Usage
To first make sure the plugin is working, do `/mobcoin help` which should bring up all the commands for the plugin. It will give a brief description of what each command will do.

Here I will go over what each command does:
*note arguments with [] are optional and ones with <> are required*

`/mobcoin balance [player]`

*This command displays the current mobcoin balance for the player. When passed a player's name as an argument, it will show the balance of that player instead.*

`/mobcoins give <player> <amount>`

*This command gives a player a set amount of mobcoins. It will add to their current balance. It is an admin command so an admin permission is required*

`/mobcoins set <player> <amount>`

*This command sets the balance of a player's mobcoins. It will override the current mobcoin balance and make it the amount passed through this command. Also an admin command*

`/mobcoins remove <player> <amount>`

*This command will remove a set amount of mobcoins from a players balance. However many mobcoins the player has will be reduced by the amount passed through the command. Also an admin command.*

`/mobcoins withdraw <amount>`

*This command allows you to withdraw any amount of mobcoins you have in your balance. It will remove those from your balance and turn them into physical tokens. These tokens can then be traded with other players. Right-clicking the token redeems it for how many you have in your inventory and adds them to your balance.*

## Permissions
Here are all the permissions for every command:
```yml  
mobcoins.openmenu:
  description: Allows the user to open the MobCoin shop
  default: op
mobcoins.checkbal:
  description: Check your mobcoin balance
  default: op
mobcoins.withdrawcoins:
  description: Withdraw coins from your balance
  default: op
mobcoins.admin:
  description: The admin perm for all admin commands
  default: op
  children:
    mobcoins.givecoins:
      description: Give coins to a player
    mobcoins.removecoins:
      description: Remove coins from a player
    mobcoins.setcoins:
      description: Set a players mobcoins 
```

## Configuring the plugin
Here I will go over each section in the config and state what it controls:

```yml
Options:
  Inventory:
    size: 54
    borderSlots: [0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 50, 51, 52, 53]
    name: "Mob Coins Shop"
```
This section controls the shop GUI. The size controls how the big the inventory will be, must be a multiple of 9. The borderSlots are the slots in the inventory to set borders, which are used for decoration. The name is the name of the shop GUI

```yml
Options:
  ItemToken:
    material: DOUBLE_PLANT
    name: "&b&lMob Coin &7(Right Click!)"
    lore:
      - '&7&oMobcoins can be used in the shop to'
      - '&7&obuy limited items'
      - ''
      - '&7&o(Right Click to redeem)'
```
This controls the token you get from withdrawing mobcoins. The material is the material enum of the item. The name is the name of the actual item. The lore is the lore on the item

```yml
Options:
  Sound:
    inventoryOpen:
      name: BAT_TAKEOFF
      volume: 1.0
      pitch: 0.5
    addCoins:
      name: FIREWORK_TWINKLE
      volume: 1.0
      pitch: 1.5
    withdrawCoins:
      name: FIREWORK_BLAST
      volume: 1.0
      pitch: 1.5
    successfulPurchase:
      name: LEVEL_UP
      volume: 1.0
      pitch: 1.5
```
This controls all the sounds at certain events. The name explains when it is played. The name is the sound enum to be played. The volume is how loud the sound will be. And the pitch is what pitch the sound is played at.

```yml
Options:
  Border:
    material: STAINED_GLASS_PANE
    damage: 15
    name: ' '
    amount: 1
```
This sets the item used for the border in the inventory. The material is the material of the item. The damage is the data value for the item, so 15 means red glass. The name is the name of the item. And the amount is the amount of items to display.

```yml
Options:
  Info:
    slot: 49
    material: EMPTY_MAP
    name: '&b&lINFO'
    lore:
      - ''
      - '&7Mob Coins are earned by killing hostile'
      - '&7mobs, with certain mobs having higher'
      - '&7chances to drop Mob Coins.'
      - ''
      - '&b&lMob Coin Chances:'
      - '&8× &7Skeletons &b5%'
      - '&8× &7Zombies &b5%'
      - '&8× &7Blazes &b5%'
      - ''
      - '&8× &7Creepers &b8%'
      - '&8× &7Endermen &b8%'
      - ''
      - '&8× &7IronGolem &b50%'
```
This controls the information item in the shop GUI. The slot is which slot to put this item in. The material is the material of the item. The name is the name of the item. And the lore is the lore of the item.

```yml
Options:
  Shop:
    Cobblestone:
      slot: 20
      price: 25
      friendlyName: "Cobblestone"
      commands:
        - 'give %player% cobblestone'
      Item:
        material: COBBLESTONE
        name: '&d&lCobblestone'
        amount: 64
        lore:
          - ""
          - "&7Purchase this item from the Mob Coin Shop"
          - ""
          - "&8× &7Price: &b%price%"
          - ""
```

This controls what is displayed in the shop. The Cobblestone defines a new item in the shop. Copy that segment of yml and change the variables to create a new item. The slot defines where the item is in the GUI. The price defines how many mobcoins are needed to buy the item. The friendly name is what the item is called without formatting. The commands are a list of commands to execute when the item is bought. The item defines the item displayed in the shop. All the values explain themselves.

```yml
Options:
  Mobs:
    SKELETON: 5
    ZOMBIE: 5
    BLAZE: 5
    CREEPER: 8
    ENDERMAN: 8
    IRON_GOLEM: 50
```
Lastly this defines the mobs you can earn coins from. Each mob is defined as the enum for the entity. Then it defines the chance of earning a mobcoin when killing that mob.

## Contributing to this project
If you want to make additions to this project follow these steps:
1. Fork this project.
2. Make your own edits to your project and update the fork.
3. Submit a new pull request on this branch and click `compare across forks`.
4. Select your forked repository and create the pull request.
5. The request will then be assessed and added to the project.

## Contributors to this project
* Illuzionz (Author)

## Status
This project is here for anyone to contribute to but I cannot promise any updates or new builds in the future.
