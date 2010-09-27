package noc.frame.dbpersister;

public interface SqlHelper {

	String getTableName();

	String builderCount();

	String builderList();

	String builderDrop();

	String builderGetMeta();

	String builderCreate();

	String builderInsert();

	String builderUpdate();

	String builderDelete();

	String builderGet();

	DbColumn[] builderColumns();
	DbColumn[]  getKeyColumns();

}
