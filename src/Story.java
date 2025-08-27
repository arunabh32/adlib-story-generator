

public class Story {
    public String noun;
    public String verb;
    public String adverb;
    public String adjective;

public Story(String noun, String verb, String adverb, String adjective) {
    this.noun = noun;
    this.verb = verb;
    this.adverb = adverb;
    this.adjective = adjective;
}
public String fillTemplate(String template){
    return template.replace("{noun}",noun)
                    .replace("{verb}", verb)
                    .replace("{adverb}", adverb)
            .replace("{adjective}", adjective);
}

}