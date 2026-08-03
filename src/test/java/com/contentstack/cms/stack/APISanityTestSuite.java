package com.contentstack.cms.stack;

import com.contentstack.cms.organization.OrgApiTests;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

/**
 * API sanity suite, ordered by module dependency (mirrors the JS CMA SDK
 * sanity suite order — see docs/DYNAMIC-TEST-FIXTURES.md):
 *
 * <pre>
 * stack/endpoint → locales → environments → assets → taxonomies
 *   → global fields → content types → entries → variants
 *   → roles → tokens → releases → org
 * </pre>
 *
 * With the dynamic fixture seeder (DYNAMIC_STACK=true) baseline data exists
 * before any test runs; this ordering additionally guarantees that whatever
 * a module test creates is available to the modules after it.
 */
@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class)
@SelectClasses({
        // Phase A: stack-level surface
        EndpointAPITest.class,
        StackAPITest.class,
        // Phase B: prerequisites for everything else
        LocaleAPITest.class,
        EnvironmentAPITest.class,
        AssetAPITest.class,
        TaxonomyAPITest.class,
        // Phase C: schema (global fields before content types)
        GlobalFieldAPITest.class,
        GlobalFieldRealAPITest.class,
        ContentTypeAPITest.class,
        ContentTypeRealAPITest.class,
        ExtensionAPITest.class,
        // Phase D: content (entries need content types + assets + environments)
        EntryFieldsAPITest.class,
        EntryRealAPITest.class,
        // Phase E: variants (need entries)
        VariantGroupAPITest.class,
        VariantGroupTest.class,
        EntryVariantAPITest.class,
        // Phase F: governance + publishing (roles/tokens need environments)
        RoleAPITest.class,
        TokenAPITest.class,
        ReleaseAPITest.class,
        // Phase G: org-level
        OrgApiTests.class
})
public class APISanityTestSuite {

}
