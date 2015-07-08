
```

import org.modsl.core.lang.uml.*;
import org.modsl.core.render.*;
...

// initializing rendering style configuration 
// (needs to be done once, on application startup)
StyleLoader stl = new StyleLoader();
stl.load("cfg/uml:cfg", "uml", UMLMetaType.class);

// creating thread-safe DSL translator instance
UMLTranslator translator = new UMLTranslator();

// parsing input and returning BufferedImage result
BufferedImage image = translator.translate("class diagram cd { class c { } }")

// writing the resulting image to disk as PNG file
ImageIO.write(image, "png", "diagram.png");

```