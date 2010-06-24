package noc.frame.lang.annotation;


public @interface Data {
	@interface Length{
		int value();
	}
	
	@interface Required{
	}

	@interface Range{
		String[] value();
	}
	
	@interface ValueList{
		String[] value();
	}

	@interface BasicInfo{
	}
	
	@interface DetailInfo{
	}
	
	@interface DescriptionInfo{
	}
	
	@interface ExtendsInfo{
	}
	
}
