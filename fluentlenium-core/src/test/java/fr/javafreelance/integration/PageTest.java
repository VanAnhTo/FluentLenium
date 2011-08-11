/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package fr.javafreelance.integration;

import fr.javafreelance.fluentlenium.core.FluentPage;
import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.ComparisonFailure;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static fr.javafreelance.fluentlenium.core.FluentPage.assertAt;
import static fr.javafreelance.fluentlenium.core.FluentPage.go;
import static org.fest.assertions.Assertions.assertThat;

public class PageTest extends LocalFluentCase {

    public class PageAccueil extends FluentPage {

        public PageAccueil(WebDriver driver) {
            super(driver);
        }

        @Override
        public String getUrl() {
            return DEFAULT_URL;
        }

        @Override
        public void isAt() {
            assertThat($("title").first().getText()).contains("Selenium");
        }

        public void goToNexPage() {

            $("#linkToPage2").click();
        }
    }

    public class Page2 extends FluentPage {

        public Page2(WebDriver driver) {
            super(driver);
        }

        @Override
        public String getUrl() {
            return DEFAULT_URL + "/page2.html";
        }

        @Override
        public void isAt() {
            assertThat($("title").first().getText()).isEqualTo("Page 2");
        }

    }


    @Test
    public void checkGoTo() {
        PageAccueil page = new PageAccueil(getDriver());
        page.go();
        assertThat(title()).contains("Selenium");
    }

    @Test
    public void checkIsAt() {
        PageAccueil page = new PageAccueil(getDriver());
        page.go();
        page.isAt();
    }

    @Test(expected = ComparisonFailure.class)
    public void checkIsAtFailed() {
        PageAccueil page = new PageAccueil(getDriver());
        page.go();
        Page2 page2 = new Page2(getDriver());
        page2.isAt();
    }

    @Test
    public void checkFollowLink() {
        PageAccueil pageAccueil = new PageAccueil(getDriver());
        Page2 page2 = new Page2(getDriver());
        pageAccueil.go();
        pageAccueil.goToNexPage();
        page2.isAt();
    }

    @Test
    public void checkFollowLinkWithBddStyle() {
        PageAccueil pageAccueil = new PageAccueil(getDriver());
        Page2 page2 = new Page2(getDriver());
        go(pageAccueil);
        FluentPage.assertAt(pageAccueil);
        pageAccueil.goToNexPage();
        assertAt(page2);
    }
}
