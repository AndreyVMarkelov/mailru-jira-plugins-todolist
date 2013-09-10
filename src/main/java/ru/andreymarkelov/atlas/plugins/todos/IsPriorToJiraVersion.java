package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.atlassian.sal.api.ApplicationProperties;

public class IsPriorToJiraVersion implements Condition {
    private int maxMajorVersion;
    private int maxMinorVersion;
    private int majorVersion;
    private int minorVersion;
 
    public IsPriorToJiraVersion(ApplicationProperties applicationProperties) {
        Matcher versionMatcher = Pattern.compile("^(\\d+)\\.(\\d+)").matcher(applicationProperties.getVersion());
        versionMatcher.find();
        majorVersion = Integer.decode(versionMatcher.group(1));
        minorVersion = Integer.decode(versionMatcher.group(2));
    }
 
    public void init(final Map<String, String> paramMap) throws PluginParseException {
        maxMajorVersion = Integer.decode(paramMap.get("majorVersion"));
        maxMinorVersion = Integer.decode(paramMap.get("minorVersion"));
    }

    public boolean shouldDisplay(final Map<String, Object> context) {
        if (majorVersion > maxMajorVersion) return false;
        if (majorVersion == maxMajorVersion && minorVersion >= maxMinorVersion) return false;
        return true;
    }
}
