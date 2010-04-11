package noc.freemarker;

import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;

public class FlexModel implements TemplateScalarModel,TemplateBooleanModel,TemplateHashModelEx,TemplateHashModel,TemplateCollectionModel,TemplateSequenceModel {

	
	FlexModel parent = null;
	final String name;

	public FlexModel(FlexModel parent,String name){
		this(name);
		this.parent = parent;
	}
	
	public FlexModel(String name){
		this.name = name;
	}
		
	@Override public TemplateCollectionModel keys() throws TemplateModelException {
		return new FlexModel(parent,"keys");
	}

	@Override public int size() throws TemplateModelException {
		return 1;
	}

	@Override public TemplateCollectionModel values() throws TemplateModelException {
		return new FlexModel(parent,"values");
	}

	@Override public TemplateModel get(String key) throws TemplateModelException {
		return new FlexModel(this,key);
	}

	@Override public boolean isEmpty() throws TemplateModelException {
		return false;
	}

	@Override public TemplateModelIterator iterator() throws TemplateModelException {
		final FlexModel parent = this;
		return new TemplateModelIterator(){
			int i=0;
			@Override
			public boolean hasNext() throws TemplateModelException {
				return i<1;
			}

			@Override
			public TemplateModel next() throws TemplateModelException {
				i++;
				return new FlexModel(parent,String.valueOf(i));
			}
			
		};
	}

	@Override public TemplateModel get(int index) throws TemplateModelException {
		return new FlexModel(parent,String.valueOf(index));
	}

	@Override public String toString() {
		return this.parent !=null ? this.parent.toString() + "." + this.name:this.name;
	}

	@Override public String getAsString() throws TemplateModelException {
		return this.toString();
	}

	@Override public boolean getAsBoolean() throws TemplateModelException {
		return true;
	}
	
	

}
