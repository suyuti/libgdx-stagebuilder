#StageBuilder
This is a library project for building libgdx stages (screens) from xml files.

#Features
* Supports basic scene2d widgets (Image, Label, Button, TextButton)
* Allows grouping of widgets.
* External xml files can be included. (scene2d groups can be reused across screens)
* Multiple screen size support.
* Screen orientation change support

##Basic scene2d widget construction

###Button & TextButton

```xml
<button name="backButton" x="10" y="420" atlas="common.atlas" frameUp="back_button" frameDown="back_buttonH"/>
```

```xml
<textbutton name="x" atlas="common.atlas" frameUp="green_button" frameDown="green_buttonH" x="270" y="340" witdth="400" text="Click Me" fontName="default_font.fnt" fontColor="000000" fontScale="1.4"/>
```

###Image
```xml
<image name="logo" x="600" y="0" atlas="common.atlas" frame="androidlogo" rotation="-30" scale="2"/>
```

###Label
```xml
<label name="test_label" x="400" y="100" width="400"  align="center"  text="This is a test label." fontScale="1.5f" fontName="default_font.fnt" fontColor="00ff00"/>
```

##Scene2d Groups
```xml
    <group name="starGroup" x="650" y="350">
        <!-- coordinates in a group are relative to group coordinates -->
        <image name="star1" x="0" y="0" atlas="common.atlas" frame="star" />
        <image name="star2" x="0" y="30" atlas="common.atlas" frame="star" />
        <image name="star3" x="30" y="0" atlas="common.atlas" frame="star" />
        <image name="star4" x="30" y="30" atlas="common.atlas" frame="star" />
    </group>
```

##Reusing scene2d groups
```xml
<Popup name="popup" x="200" y="140" visible="false"/>
```
**Popup.xml** file will be included and a scene2d group will be created with the information defined in Popup.xml file. The group will be positioned at 200,140 and will be hidden.

##Multiple screen size support
TODO incomplete documentation

##Screen orientation change support
TODO incomplete documentation

#Demo
There is a demo application in **stagebuilder-desktop** project.