package fr.anto42.emma.utils.skulls;

import org.bukkit.inventory.ItemStack;

import java.util.Random;

public enum SkullList {
    BEDROCK("Bedrock", "36d1fabdf3e342671bd9f95f687fe263f439ddc2f1c9ea8ff15b13f1e7e48b9"),
    GOLDENAPPLE("Golden Apple", "8d68a32e3298fcd4eaee89088aaa1619ccf11cc2c4d5bbb66fce654733d1dd6"),
    EARTH("", "5a9d914a12c17cccb55899285a066902ba53976807407fcb8696dbe19aef77"),
    EARTH_2("", "179e2248bd798eb61f97aeeecc2afddebad52bf4052c7261b6180a4577f8693a"),
    CAMERA("", "3db83586542934f8c3231a5284f2489b87678478454fca69359447569f157d14"),
    CIBLE("", "cc6b2518ae7b60e441f8fe624994e188685d6613f28ab59ab36d1f9e9c9eee36"),
    EYE("", "45556083c9cde19f54bf78a421cb9731f60f1d3de3cf584f54b2d43677df2a7"),
    CRATE_WOOD("", "f620519b74536c1f85b7c7e5e11ce5c059c2ff759cb8df254fc7f9ce781d29"),
    CRATE_IRON("", "1682de72bf61c6d23364e2fe2d7cc28ddf83145d18f193857d369cf9df692"),
    CRATE_BLUE("", "43933a6d24c8b4679912e4ff19efa56a8c970d664f6e6e4f6a2d067d5b8b"),
    CRATE_GOLD("", "cdf81449131dcdd3578899fcd9592e13f5cca57ae7481fd6710bb6ca85d65c9"),
    CHEST("Chest", "500ecf693eb2aadd6c371ae3594de65818616f77ab08e51dac42d70444f0d52d"),
    SNAKE("", "51b4b20189d902bc6c3fabe23d9f578697f544cf415317787374118393da4"),
    LUCKYBLOCK("", "cdd1e6bd215afa5e673285afacb85eb8d0f79a5b46c5432d6feed66097c51248"),
    MASCOTTE_COMPUTER("Computer", "e78e6a83ffa3a169fc7a12c6f6ac931ff1b212c360a56774e4c8d4e269b94e5b"),
    GIFT("Gift", "473ba546a62b32c515376dd7f3c92fb35a33524f323bbb2b5978e38c7e5f62a"),
    BLOCK_COMMANDBLOCK_DEFAULT("", "27712ca655128701ea3e5f28ddd69e6a8e63adf28052c51b2fd5adb538e1"),
    BLOCK_COMMANDBLOCK_GREEN("", "285cd8b374fa99e6acc677fcc8d9f4db90e3c337436aea26afe6e13acbdf9"),
    COMMANDBLOCK_RED("", "f0ab7f2ceb70b075b8f9c880a6691947f87fad3eb91fdffb07cab04767c3e14b"),
    YOUTUBE("", "b4353fd0f86314353876586075b9bdf0c484aab0331b872df11bd564fcb029ed"),
    DISCORD("", "b2efa83c998233e9deaf7975ace4cd16b6362a859d5682c36314d1e60af"),
    FACEBOOK("", "deb46126904463f07ecfc972aaa37373a22359b5ba271821b689cd5367f75762"),
    TWITTER("", "3685a0be743e9067de95cd8c6d1ba21ab21d37371b3d597211bb75e43279"),
    RED_BALL("", "533a5bfc8a2a3a152d646a5bea694a425ab79db694b214f156c37c7183aa"),
    ORANGE_BALL("", "adc56355f11ce53e14d374ecf2a0b255301b734d99c674240afacc73e2145c"),
    YELLOW_BALL("", "41139b3ef2e4c44a4c983f114cbe948d8ab5d4f879a5c665bb820e7386ac2f"),
    LIME_BALL("", "c607cffcd7864ff27c78b29a2c5955131a677bef6371f88988d3dcc37ef8873"),
    GREEN_BALL("", "85484f4b6367b95bb16288398f1c8dd6c61de988f3a8356d4c3ae73ea38a42"),
    CYAN_BALL("", "cb9b2a4d59781d1bec2d8278f23985e749c881b72d7876c979e71fda5bd3c"),
    TURQUOISE_BALL("", "4f26ea93d5fd19a3808a5e5885fc29612657d83dfab9bff527279ccbd6f16"),
    BLUE_BALL("", "d1137b9bf435c4b6b88faeaf2e41d8fd04e1d9663d6f63ed3c68cc16fc724"),
    PURPLE_BALL("", "a1b9a0a6d1b9912794289eca1e224eae6d76a7cb752ca689f1b991ce970adee"),
    MAGENTA_BALL("", "3dd0115e7d84b11b67a4c6176521a0e79d8ba7628587ae38159e828852bfd"),
    BOOKSHELF("", "cea632e26374f23b73a7283dc1d58e957c3a126cdbba90747084658afbdff3b"),
    PINK_BALL("", "c54deeeab3b9750c77642ec95e37fe2bdf9adc555e0826dedd769bedd10"),
    BROWN_BALL("", "eaa628e0ed229a4d782c319debdb64af9ef910d52bf9e91458544ccfd1602d"),
    WHITE_BALL("", "caf039bec1fc1fb75196092b26e631f37a87dff143fc18297798d47c5eaaf"),
    LIGHTGRAY_BALL("", "145fd2c3736af4bd2811296661e0cb49bab2cfa65f5c9e598aa43bebfa1ea368"),
    GRAY_BALL("", "da3d7a709717d1ffe2402448d867b1a7f32fb8955621cc8977b61f9a3cbc95"),
    BLACK_BALL("", "3e1e5c81fb9d64b74996fd171489deadbb8cb772d52cf8b566e3bc102301044"),
    ENDERDRAGON_BALL("", "87cf21ccb21e2d29c81cc15fe8d3b3ef971d182d3224a212964dddb36cf4"),
    LEFT_AROOW("left_arrow", "62ffa98415fe5e0180147204b6184bd79c1636862b26774135b345aa6f145479"),
    RIGHT_ARROW("right_arrow", "72f88335195d9994d711b900de251c9e59ba596fe6441df409a028fad232b5f6"),
    DIAMOND_BALL("", "a399c9d599e0d9dc6a480e85f4dbecc45b318814026895ac8150fd2e2fa2599e"),
    IRON_BALL("", "174858c976f0274ebce3f3ffcef653609f29d37e0cc9cad25e586864b806cb23"),
    TIMER("", "4e1ab66e8c259ba980111a75e74264535204d52f145f8acf08fadb167aef9b0"),
    DEAD_STEVE("dead_steve", "b1637016f91f8388ffdcd3778e8be7bdb16d7a37046b79a5218bf5da9f5d7f2a"),
    HEART("heart_ball", "7dfe38599fe369862fe56b83fca25b831ea2a348b029bfb24b987dc92e875658"),
    XP("", "3255327dd8e90afad681a19231665bea2bd06065a09d77ac1408837f9e0b242");

    private String name;
    private String texture;

    private SkullList(String name, String texture) {
        this.name = name;
        this.texture = texture;
    }

    public String getName() {
        return this.name;
    }

    public String getTextureLink() {
        return "http://textures.minecraft.net/texture/" + this.getTextureHash();
    }

    public String getTextureHash() {
        return this.texture;
    }

    public ItemStack getItemStack() {
        return SkullUtils.getSkullByURL(this.getTextureLink());
    }

    public ItemStack getItemStack(String displayname) {
        return SkullUtils.getSkullByURL(this.getTextureLink(), displayname);
    }

    public ItemStack getItemStack(String displayname, String... lores) {
        return SkullUtils.getSkullByURL(this.getTextureLink(), displayname, lores);
    }

    public ItemStack getItemStack(String displayname, String lore) {
        return SkullUtils.getSkullByURL(this.getTextureLink(), displayname, lore);
    }

    public ItemStack getItemStackWithName() {
        return SkullUtils.getSkullByURL(this.getTextureLink(), this.getName());
    }

    public static ItemStack getRandomSkull() {
        int r = (new Random()).nextInt(values().length);
        return SkullUtils.getSkullByURL(values()[r].getTextureLink());
    }

    public static ItemStack getRandomSkull(String displayname) {
        int r = (new Random()).nextInt(values().length);
        return SkullUtils.getSkullByURL(values()[r].getTextureLink(), displayname);
    }

    public static ItemStack getRandomSkull(String displayname, String... lores) {
        int r = (new Random()).nextInt(values().length);
        return SkullUtils.getSkullByURL(values()[r].getTextureLink(), displayname, lores);
    }

    public static ItemStack getRandomSkull(String displayname, String lore) {
        int r = (new Random()).nextInt(values().length);
        return SkullUtils.getSkullByURL(values()[r].getTextureLink(), displayname, lore);
    }
}
