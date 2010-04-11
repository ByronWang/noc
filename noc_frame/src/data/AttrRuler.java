package data;

import noc.annotation.Catalog;
import noc.annotation.FrameType;
import noc.annotation.Inline;
import noc.annotation.PrimaryKey;
import noc.lang.List;
import noc.lang.Name;
import noc.lang.reflect.Type;

@FrameType public class AttrRuler {
	@Catalog Type type;
	@PrimaryKey Name name;
	@Inline List<Item> items;

	public class Item {
		Name name;
		Name displayName;
	}
}
