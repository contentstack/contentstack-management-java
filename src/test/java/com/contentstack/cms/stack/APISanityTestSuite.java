package com.contentstack.cms.stack;
 import com.contentstack.cms.organization.OrgApiTests;
 import org.junit.platform.runner.JUnitPlatform;
 import org.junit.platform.suite.api.SelectClasses;
 import org.junit.runner.RunWith;


 @SuppressWarnings("deprecation")
 @RunWith(JUnitPlatform.class)
@SelectClasses({
        EndpointAPITest.class,
        TaxonomyAPITest.class,
        AssetAPITest.class,
        ContentTypeAPITest.class,
        ContentTypeRealAPITest.class,
        EntryFieldsAPITest.class,
        EntryRealAPITest.class,
        EnvironmentAPITest.class,
        ExtensionAPITest.class,
        LocaleAPITest.class,
        RoleAPITest.class,
        StackAPITest.class,
        TokenAPITest.class,
        OrgApiTests.class,
        GlobalFieldAPITest.class,
        GlobalFieldRealAPITest.class,
        VariantGroupAPITest.class,
        VariantGroupTest.class,
        EntryVariantAPITest.class,
        ReleaseAPITest.class

})
 public class APISanityTestSuite {

 }