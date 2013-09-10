package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.atlassian.sal.api.ApplicationProperties;

public class IsAtLeastJiraVersion implements Condition {
    private int minMajorVersion;
    private int minMinorVersion;
    private int majorVersion;
    private int minorVersion;

    public IsAtLeastJiraVersion(final ApplicationProperties applicationProperties) {
        Matcher versionMatcher = Pattern.compile("^(\\d+)\\.(\\d+)").matcher(applicationProperties.getVersion());
        versionMatcher.find();
        majorVersion = Integer.decode(versionMatcher.group(1));
        minorVersion = Integer.decode(versionMatcher.group(2));
    }

    public void init(final Map<String, String> paramMap) throws PluginParseException {
        minMajorVersion = Integer.decode(paramMap.get("majorVersion"));
        minMinorVersion = Integer.decode(paramMap.get("minorVersion"));
    }

    public boolean shouldDisplay(final Map<String, Object> context) {
        if (majorVersion < minMajorVersion) return false;
        if (majorVersion == minMajorVersion && minorVersion < minMinorVersion) return false;
        return true;
    }
}
