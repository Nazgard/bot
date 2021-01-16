package dev.makarov.bot.rocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RocketSongEmotion {

    private static final Map<String, String> MAP = new HashMap<>();
    private static final List<String> DEFAULT = new ArrayList<>();

    static {
          MAP.put("alien", ":alien:");
          MAP.put("ufo", ":alien:");
          MAP.put("heartbreak", ":heartbreak:");
          MAP.put("heart break", ":heartbreak:");
          MAP.put("broken heart", ":heartbreak:");
          MAP.put("space", ":alien:");
          MAP.put("innocent", ":innocent:");
          MAP.put("evil", ":smiling_imp:");
          MAP.put("yum", ":yum:");
          MAP.put("yummy", ":yum:");
          MAP.put("delicious", ":yum:");
          MAP.put("cupid", ":cupid:");
          MAP.put("sweat", ":sweat:");
          MAP.put("sweaty", ":sweat:");
          MAP.put("workout", ":muscle:");
          MAP.put("work out", ":muscle:");
          MAP.put("star", ":star:");
          MAP.put("cat", ":cat:");
          MAP.put("kitty", ":cat:");
          MAP.put("pig", ":pig:");
          MAP.put("monkey", ":monkey:");
          MAP.put("sheep", ":sheep:");
          MAP.put("boom", ":boom:");
          MAP.put("explosion", ":boom:");
          MAP.put("crash", ":boom:");
          MAP.put("fart", ":dash:");
          MAP.put("poop", ":poop:");
          MAP.put("shit", ":poop:");
          MAP.put("muscle", ":muscle:");
          MAP.put("run", ":runner:");
          MAP.put("running", ":runner:");
          MAP.put("runner", ":runner:");
          MAP.put("clap", ":clap:");
          MAP.put("kiss", ":kiss:");
          MAP.put("lips", ":lips:");
          MAP.put("sleeping", ":sleeping:");
          MAP.put("sleep", ":sleeping:");
          MAP.put("sea", ":ocean:");
          MAP.put("ocean", ":ocean:");
          MAP.put("wave", ":ocean:");
          MAP.put("waves", ":ocean:");
          MAP.put("water", ":ocean:");
          MAP.put("waters", ":ocean:");
          MAP.put("tired", ":sleeping:");
          MAP.put("looking", ":eyes:");
          MAP.put("money", ":money_with_wings:");
          MAP.put("millionaire", ":money_with_wings:");
          MAP.put("billionaire", ":money_with_wings:");
          MAP.put("rage", ":rage1:");
          MAP.put("angry", ":angry:");
          MAP.put("anger", ":angry:");
          MAP.put("mad", ":angry:");
          MAP.put("hate", ":angry:");
          MAP.put("sunny", ":sunny:");
          MAP.put("sun", ":sunny:");
          MAP.put("snow", ":snowflake:");
          MAP.put("snowflake", ":snowflake:");
          MAP.put("snowy", ":snowflake:");
          MAP.put("rain", ":droplet:");
          MAP.put("rainy", ":droplet:");
          MAP.put("raining", ":droplet:");
          MAP.put("cloud", ":cloud:");
          MAP.put("cloudy", ":cloud:");
          MAP.put("moon", ":full_moon:");
          MAP.put("cool", ":sunglasses:");
          MAP.put("wind", ":dash:");
          MAP.put("fast", ":dash:");
          MAP.put("eyes", ":eyes:");
          MAP.put("eye", ":eyes:");
          MAP.put("hello", ":wave:");
          MAP.put("love", ":heart:");
          MAP.put("heart", ":heart:");
          MAP.put("ghost", ":ghost:");
          MAP.put("scary", ":ghost:");
          MAP.put("halloween", ":ghost:");
          MAP.put("fire", ":fire:");
          MAP.put("hot", ":fire:");
          MAP.put("burn", ":fire:");
          MAP.put("night", ":sparkles:");
          MAP.put("forest", ":evergreen_tree:");
          MAP.put("tree", ":evergreen_tree:");
          MAP.put("hug", ":hugging_face:");
          MAP.put("hugging", ":hugging_face:");

          DEFAULT.add(":headphones:");
          DEFAULT.add(":notes:");
          DEFAULT.add(":musical_note:");
    }

    public static String getEmotion(String name) {
        String lowerCaseName = name.toLowerCase();
        return MAP.keySet()
                .stream()
                .filter(lowerCaseName::contains)
                .map(MAP::get)
                .findAny()
                .orElse(DEFAULT.get(ThreadLocalRandom.current().nextInt(0, DEFAULT.size() - 1)));
    }

}
