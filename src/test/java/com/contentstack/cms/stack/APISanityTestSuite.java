package com.contentstack.cms.stack;
 import com.contentstack.cms.organization.OrgApiTests;
 import org.junit.platform.runner.JUnitPlatform;
 import org.junit.platform.suite.api.SelectClasses;
 import org.junit.runner.RunWith;


 @SuppressWarnings("deprecation")
 @RunWith(JUnitPlatform.class)
@SelectClasses({
        TaxonomyAPITest.class,
        AssetAPITest.class,
        ContentTypeAPITest.class,
        ContentTypeRealAPITest.class,  // Real API tests with actual CRUD operations
        EntryFieldsAPITest.class,
        EntryRealAPITest.class,  // Real API tests with actual CRUD operations
        EnvironmentAPITest.class,
        ExtensionAPITest.class,
        LocaleAPITest.class,
        RoleAPITest.class,
        StackAPITest.class,
        TokenAPITest.class,
        OrgApiTests.class,
        GlobalFieldAPITest.class,
        GlobalFieldRealAPITest.class,  // Real API tests with actual CRUD operations
        VariantGroupAPITest.class,
        VariantGroupTest.class,
        ReleaseAPITest.class

})
 public class APISanityTestSuite {

 }