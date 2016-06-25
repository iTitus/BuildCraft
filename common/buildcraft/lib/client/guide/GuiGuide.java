package buildcraft.lib.client.guide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.google.common.base.Throwables;
import com.google.common.collect.Queues;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import buildcraft.lib.client.guide.font.FontManager;
import buildcraft.lib.client.guide.font.IFontRenderer;
import buildcraft.lib.client.guide.parts.GuideChapter;
import buildcraft.lib.client.guide.parts.GuidePageBase;
import buildcraft.lib.client.guide.parts.GuidePageContents;
import buildcraft.lib.gui.GuiIcon;
import buildcraft.lib.gui.GuiRectangle;
import buildcraft.lib.gui.IPositionedElement;
import buildcraft.lib.gui.MousePosition;

public class GuiGuide extends GuiScreen {
    public static final ResourceLocation ICONS_1 = Gui.ICONS;
    public static final ResourceLocation ICONS_2 = new ResourceLocation("buildcraftcore:textures/gui/guide/icons.png");
    public static final ResourceLocation COVER = new ResourceLocation("buildcraftcore:textures/gui/guide/cover.png");
    public static final ResourceLocation LEFT_PAGE = new ResourceLocation("buildcraftcore:textures/gui/guide/left_page.png");
    public static final ResourceLocation RIGHT_PAGE = new ResourceLocation("buildcraftcore:textures/gui/guide/right_page.png");
    public static final ResourceLocation LEFT_PAGE_BACK = new ResourceLocation("buildcraftcore:textures/gui/guide/left_page_back.png");
    public static final ResourceLocation RIGHT_PAGE_BACK = new ResourceLocation("buildcraftcore:textures/gui/guide/right_page_back.png");

    public static final GuiIcon BOOK_COVER = new GuiIcon(COVER, 0, 0, 202, 248);
    public static final GuiIcon BOOK_BINDING = new GuiIcon(COVER, 204, 0, 11, 248);

    public static final GuiIcon PAGE_LEFT = new GuiIcon(LEFT_PAGE, 0, 0, 193, 248);
    public static final GuiIcon PAGE_RIGHT = new GuiIcon(RIGHT_PAGE, 0, 0, 193, 248);

    public static final GuiIcon PAGE_LEFT_BACK = new GuiIcon(LEFT_PAGE_BACK, 0, 0, 193, 248);
    public static final GuiIcon PAGE_RIGHT_BACK = new GuiIcon(RIGHT_PAGE_BACK, 0, 0, 193, 248);

    public static final GuiRectangle PAGE_LEFT_TEXT = new GuiRectangle(31, 22, 141, 193);
    public static final GuiRectangle PAGE_RIGHT_TEXT = new GuiRectangle(20, 22, 141, 193);

    public static final GuiIcon PEN_UP = new GuiIcon(ICONS_2, 0, 0, 14, 135);
    public static final GuiIcon PEN_ANGLED = new GuiIcon(ICONS_2, 17, 0, 100, 100);

    public static final GuiIcon PEN_HIDDEN_MIN = new GuiIcon(ICONS_2, 0, 4, 10, 5);
    public static final GuiIcon PEN_HIDDEN_MAX = new GuiIcon(ICONS_2, 0, 4, 10, 15);

    public static final GuiIcon TURN_BACK = new GuiIcon(ICONS_2, 23, 139, 18, 10);
    public static final GuiIcon TURN_BACK_HOVERED = new GuiIcon(ICONS_2, 23, 152, 18, 10);

    public static final GuiIcon TURN_FORWARDS = new GuiIcon(ICONS_2, 0, 139, 18, 10);
    public static final GuiIcon TURN_FORWARDS_HOVERED = new GuiIcon(ICONS_2, 0, 152, 18, 10);

    public static final GuiIcon BACK = new GuiIcon(ICONS_2, 48, 139, 17, 9);
    public static final GuiIcon BACK_HOVERED = new GuiIcon(ICONS_2, 48, 152, 17, 9);

    public static final GuiIcon BOX_EMPTY = new GuiIcon(ICONS_2, 0, 164, 16, 16);
    public static final GuiIcon BOX_MINUS = new GuiIcon(ICONS_2, 16, 164, 16, 16);
    public static final GuiIcon BOX_PLUS = new GuiIcon(ICONS_2, 32, 164, 16, 16);
    public static final GuiIcon BOX_TICKED = new GuiIcon(ICONS_2, 48, 164, 16, 16);
    public static final GuiIcon BOX_CHAPTER = new GuiIcon(ICONS_2, 64, 164, 16, 16);

    public static final GuiIcon BOX_SELECTED_EMPTY = new GuiIcon(ICONS_2, 0, 180, 16, 16);
    public static final GuiIcon BOX_SELECTED_MINUS = new GuiIcon(ICONS_2, 16, 180, 16, 16);
    public static final GuiIcon BOX_SELECTED_PLUS = new GuiIcon(ICONS_2, 32, 180, 16, 16);
    public static final GuiIcon BOX_SELECTED_TICKED = new GuiIcon(ICONS_2, 48, 180, 16, 16);
    public static final GuiIcon BOX_SELECTED_CHAPTER = new GuiIcon(ICONS_2, 64, 180, 16, 16);

    public static final GuiIcon BORDER_TOP_LEFT = new GuiIcon(ICONS_2, 0, 196, 13, 13);
    public static final GuiIcon BORDER_TOP_RIGHT = new GuiIcon(ICONS_2, 13, 196, 13, 13);
    public static final GuiIcon BORDER_BOTTOM_LEFT = new GuiIcon(ICONS_2, 0, 209, 13, 13);
    public static final GuiIcon BORDER_BOTTOM_RIGHT = new GuiIcon(ICONS_2, 13, 209, 13, 13);

    public static final GuiIcon ORDER_TYPE = new GuiIcon(ICONS_2, 14, 100, 14, 14);
    public static final GuiIcon ORDER_STAGE = new GuiIcon(ICONS_2, 28, 100, 14, 14);
    public static final GuiIcon ORDER_MOD_TYPE = new GuiIcon(ICONS_2, 42, 100, 14, 14);
    public static final GuiIcon ORDER_MOD = new GuiIcon(ICONS_2, 56, 100, 14, 14);

    public static final GuiIcon CHAPTER_MARKER_LEFT = new GuiIcon(ICONS_2, 0, 223, 5, 16);
    public static final GuiIcon CHAPTER_MARKER_SPACE = new GuiIcon(ICONS_2, 6, 223, 19, 16);
    public static final GuiIcon CHAPTER_MARKER_RIGHT = new GuiIcon(ICONS_2, 27, 223, 5, 16);

    public static final GuiIcon[] ORDERS = { ORDER_TYPE, ORDER_STAGE, ORDER_MOD_TYPE, ORDER_MOD };

    public static final GuiRectangle BACK_POSITION = new GuiRectangle(PAGE_LEFT.u + PAGE_LEFT.width - BACK.width / 2, PAGE_LEFT.v + PAGE_LEFT.height - BACK.height - 2, BACK.width, BACK.height);

    public static final TypeOrder[] SORTING_TYPES = { //
        new TypeOrder(ETypeTag.TYPE, ETypeTag.SUB_TYPE),//
        new TypeOrder(ETypeTag.MOD, ETypeTag.TYPE),//
        new TypeOrder(ETypeTag.MOD, ETypeTag.SUB_MOD),//
    };

    // REMOVE FROM HERE...
    private static final int BOOK_DOUBLE_WIDTH = 386, BOOK_DOUBLE_HEIGHT = 248;

    private static final int PEN_HIDDEN_Y = 0, PEN_HIDDEN_X = 4, PEN_HIDDEN_WIDTH = 10;
    private static final int PEN_HIDDEN_HEIGHT_MIN = 5, PEN_HIDDEN_HEIGHT_MAX = 15;
    // TO HERE

    public static final GuiRectangle PEN_HIDDEN_AREA = new GuiRectangle(PAGE_LEFT.width - PEN_HIDDEN_WIDTH / 2, -PEN_HIDDEN_HEIGHT_MAX, PEN_HIDDEN_WIDTH, PEN_HIDDEN_HEIGHT_MAX);

    // private static final int PEN_HIDDEN_BOX_X_MIN = PAGE_LEFT.width - PEN_HIDDEN_WIDTH / 2;
    // private static final int PEN_HIDDEN_BOX_Y_MIN = -PEN_HIDDEN_HEIGHT_MAX;
    // private static final int PEN_HIDDEN_BOX_X_MAX = PAGE_LEFT.width + PEN_HIDDEN_WIDTH / 2;
    // private static final int PEN_HIDDEN_BOX_Y_MAX = 0;

    private static final float PEN_HOVER_TIME = 9f;
    private static final float BOOK_OPEN_TIME = 20f;

    public final MousePosition mouse = new MousePosition();

    public int sortingOrderIndex = 0;
    private boolean isOpen = false, isEditing = false;
    private boolean isOpening = false;

    /** Float between -90 and 90} */
    private float openingAngleLast = -90, openingAngleNext = -90;

    /** Float between {@link #PEN_HIDDEN_HEIGHT_MIN} and {@link #PEN_HIDDEN_HEIGHT_MAX} */
    private float hoverStageLast = 0, hoverStageNext = 0;
    private boolean isOverHover = false;

    public int minX, minY;
    public ItemStack tooltipStack = null;

    private final Deque<GuidePageBase> pages = Queues.newArrayDeque();
    private final List<GuideChapter> chapters = new ArrayList<>();
    private GuidePageBase currentPage;
    private IFontRenderer currentFont = FontManager.INSTANCE.getOrLoadFont("DejaVu:13");

    public GuiGuide() {
        openPage(new GuidePageContents(this));
    }

    public void initForExport() {
        isOpening = true;
        isOpen = true;
        setWorldAndResolution(Minecraft.getMinecraft(), 1920, 1080);
    }

    public void openPage(GuidePageBase page) {
        if (currentPage != null && currentPage.shouldPersistHistory()) {
            pages.push(currentPage);
        }
        setPageInternal(page);
    }

    public void closePage() {
        if (pages.isEmpty()) {
            mc.displayGuiScreen(null);
        } else {
            setPageInternal(pages.pop());
        }
    }

    public void goBackToMenu() {
        GuidePageBase newPage = currentPage;
        while (!pages.isEmpty()) {
            newPage = pages.pop();
        }
        setPageInternal(newPage);
    }

    private void setPageInternal(GuidePageBase page) {
        currentPage = page;
        refreshChapters();
    }

    public GuidePageBase getCurrentPage() {
        return currentPage;
    }

    public IFontRenderer getCurrentFont() {
        return this.currentFont;
    }

    public int getChapterIndex(GuideChapter chapter) {
        return chapters.indexOf(chapter);
    }

    public void refreshChapters() {
        chapters.clear();
        chapters.addAll(currentPage.getChapters());
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (isOpen) {
            // Calculate pen hover position
            float hoverDiff = (PEN_HIDDEN_HEIGHT_MAX - PEN_HIDDEN_HEIGHT_MIN) / PEN_HOVER_TIME;
            hoverStageLast = hoverStageNext;
            if (hoverStageNext > PEN_HIDDEN_HEIGHT_MAX) {
                hoverStageNext -= hoverDiff * 5;
            } else if (isOverHover) {
                hoverStageNext += hoverDiff;
                if (hoverStageNext > PEN_HIDDEN_HEIGHT_MAX) {
                    hoverStageNext = PEN_HIDDEN_HEIGHT_MAX;
                }
            } else {
                if (hoverStageNext > PEN_HIDDEN_HEIGHT_MIN) {
                    hoverStageNext -= hoverDiff;
                }
                if (hoverStageNext < PEN_HIDDEN_HEIGHT_MIN) {
                    hoverStageNext = PEN_HIDDEN_HEIGHT_MIN;
                }
            }
            currentPage.updateScreen();
            for (GuideChapter chapter : chapters) {
                chapter.updateScreen();
            }
        } else if (isOpening) {
            openingAngleLast = openingAngleNext;
            openingAngleNext += 180 / BOOK_OPEN_TIME;
        }
        if (currentPage != null) {
            setupFontRenderer();
            currentPage.tick();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        minX = (width - BOOK_DOUBLE_WIDTH) / 2;
        minY = (height - BOOK_DOUBLE_HEIGHT) / 2;
        mouse.setMousePosition(mouseX, mouseY);
        try {
            if (isOpen) {
                drawOpen(partialTicks);
            } else if (isOpening) {
                drawOpening(partialTicks);
            } else {
                drawCover();
            }
        } catch (Throwable t) {
            // Temporary fix for crash report classes crashing so we can see the ACTUAL error
            t.printStackTrace();
            throw Throwables.propagate(t);
        }
    }

    public void drawTooltip(ItemStack stack, int x, int y) {
        renderToolTip(stack, x, y);
    }

    private void drawCover() {
        minX = (width - BOOK_COVER.width) / 2;
        minY = (height - BOOK_COVER.height) / 2;

        mc.renderEngine.bindTexture(COVER);
        BOOK_COVER.drawAt(minX, minY);
    }

    private void drawOpening(float partialTicks) {
        minX = (width - BOOK_COVER.width) / 2;
        minY = (height - BOOK_COVER.height) / 2;

        float openingAngle = openingAngleLast * (1 - partialTicks) + openingAngleNext * partialTicks;
        float sin = MathHelper.sin((float) (openingAngle * Math.PI / 180));
        if (sin < 0) {
            sin *= -1;
        }
        if (openingAngle >= 90) {
            isOpen = true;
        }
        if (openingAngle < 0) {
            minX = (width - BOOK_COVER.width) / 2;
            minY = (height - BOOK_COVER.height) / 2;

            int coverWidth = (int) (sin * BOOK_COVER.width);
            sin = 1 - sin;
            int bindingWidth = (int) (sin * BOOK_BINDING.width);

            mc.renderEngine.bindTexture(RIGHT_PAGE);
            PAGE_RIGHT.drawAt(minX + BOOK_COVER.width - PAGE_RIGHT.width, minY);

            mc.renderEngine.bindTexture(COVER);
            BOOK_COVER.drawScaledInside(minX, minY, coverWidth, BOOK_COVER.height);

            BOOK_BINDING.drawScaledInside(minX + coverWidth, minY, bindingWidth, BOOK_BINDING.height);

        } else if (openingAngle == 0) {
            minX = (width - BOOK_COVER.width) / 2;
            minY = (height - BOOK_COVER.height) / 2;

            mc.renderEngine.bindTexture(RIGHT_PAGE);
            PAGE_RIGHT.drawAt(minX + BOOK_COVER.width - PAGE_LEFT.width, minY);

            mc.renderEngine.bindTexture(COVER);
            BOOK_COVER.drawAt(minX, minY);
        } else if (openingAngle > 0) {
            int pageWidth = (int) (sin * PAGE_LEFT.width);
            int bindingWidth = (int) ((1 - sin) * BOOK_BINDING.width);

            int penHeight = (int) (sin * PEN_HIDDEN_HEIGHT_MIN);

            minX = (width - BOOK_COVER.width - pageWidth) / 2;
            minY = (height - BOOK_COVER.height) / 2;

            mc.renderEngine.bindTexture(RIGHT_PAGE);
            PAGE_RIGHT.drawAt(minX + pageWidth + bindingWidth, minY);

            mc.renderEngine.bindTexture(LEFT_PAGE);
            PAGE_LEFT.drawScaledInside(minX + bindingWidth, minY, pageWidth, PAGE_LEFT.height);

            mc.renderEngine.bindTexture(COVER);
            BOOK_BINDING.drawScaledInside(minX, minY, bindingWidth, BOOK_BINDING.height);

            mc.renderEngine.bindTexture(ICONS_2);
            drawTexturedModalRect(minX + pageWidth + bindingWidth - (PEN_HIDDEN_WIDTH / 2), minY - penHeight, PEN_HIDDEN_X, PEN_HIDDEN_Y, PEN_HIDDEN_WIDTH, penHeight);
        }
    }

    private void drawOpen(float partialTicks) {
        // Draw the pages
        mc.renderEngine.bindTexture(LEFT_PAGE);
        PAGE_LEFT.drawAt(minX, minY);

        mc.renderEngine.bindTexture(RIGHT_PAGE);
        PAGE_RIGHT.drawAt(minX + PAGE_LEFT.width, minY);

        isOverHover = PEN_HIDDEN_AREA.offset(minX, minY).contains(mouse);

        // Now draw the actual contents of the book
        String title = currentPage.getTitle();
        if (title != null) {
            int x = /* this.minX + */ (width - currentFont.getStringWidth(title)) / 2;
            currentFont.drawString(title, x, minY + 12, 0);
        }

        tooltipStack = null;
        setupFontRenderer();
        for (GuideChapter chapter : chapters) {
            chapter.reset();
        }

        currentPage.renderFirstPage(minX + PAGE_LEFT_TEXT.x, minY + PAGE_LEFT_TEXT.y, PAGE_LEFT_TEXT.width, PAGE_LEFT_TEXT.height);
        currentPage.renderSecondPage(minX + PAGE_LEFT.width + PAGE_RIGHT_TEXT.x, minY + PAGE_RIGHT_TEXT.y, PAGE_RIGHT_TEXT.width, PAGE_RIGHT_TEXT.height);

        int chapterIndex = 0;
        for (GuideChapter chapter : chapters) {
            chapter.draw(chapterIndex);
            chapterIndex++;
        }

        // Draw the back button if there are any pages on the stack
        if (!pages.isEmpty()) {
            GuiIcon icon = BACK;
            IPositionedElement position = BACK_POSITION.offset(minX, minY);
            if (position.contains(mouse)) {
                icon = BACK_HOVERED;
            }
            icon.drawAt(position);
        }

        // Reset the colour for the pen
        GlStateManager.color(1, 1, 1);

        // Draw the pen
        if (isEditing) {
            mc.renderEngine.bindTexture(ICONS_2);

            if (isOverHover) {
                PEN_UP.drawAt(mouse.getX() - PEN_UP.width / 2, mouse.getY() - PEN_UP.height);
            } else {
                PEN_ANGLED.drawAt(mouse.getX() - 2, mouse.getY() - PEN_ANGLED.height - 2);
            }
        } else {
            int height = (int) (hoverStageLast * (1 - partialTicks) + hoverStageNext * partialTicks);

            // Draw pen
            mc.renderEngine.bindTexture(ICONS_2);
            drawTexturedModalRect(minX + PAGE_LEFT.width - PEN_HIDDEN_WIDTH / 2, minY - height, PEN_HIDDEN_X, PEN_HIDDEN_Y, PEN_HIDDEN_WIDTH, height);

            if (tooltipStack != null) {
                renderToolTip(tooltipStack, mouse.getX(), mouse.getY());
            }
        }
    }

    public void setupFontRenderer() {
        currentPage.setFontRenderer(currentFont);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        mouse.setMousePosition(mouseX, mouseY);
        // Primary mouse button
        if (mouseButton == 0) {
            if (isOpen) {
                int page0xMin = this.minX + PAGE_LEFT_TEXT.x;
                int page0xMax = page0xMin + PAGE_LEFT_TEXT.width;
                int page1xMin = this.minX + PAGE_LEFT.width + PAGE_RIGHT_TEXT.x;
                int page1xMax = page1xMin + PAGE_RIGHT_TEXT.width;
                int pageYMin = this.minY + PAGE_RIGHT_TEXT.y;
                int pageYMax = pageYMin + PAGE_RIGHT_TEXT.height;

                GuidePageBase current = currentPage;
                current.setFontRenderer(currentFont);

                for (GuideChapter chapter : chapters) {
                    if (chapter.handleClick()) {
                        return;
                    }
                }

                current.handleMouseClick(page0xMin, pageYMin, page0xMax - page0xMin, pageYMax - pageYMin, mouseX, mouseY, mouseButton, currentPage.getPage(), isEditing);
                current.handleMouseClick(page1xMin, pageYMin, page1xMax - page1xMin, pageYMax - pageYMin, mouseX, mouseY, mouseButton, currentPage.getPage() + 1, isEditing);

                if ((!pages.isEmpty()) && BACK_POSITION.offset(minX, minY).contains(mouse)) {
                    closePage();
                }

                if (isOverHover) {
                    isEditing = !isEditing;
                    if (!isEditing) {
                        hoverStageNext = PEN_UP.height;
                    }
                }
            } else {
                if (mouseX >= minX && mouseY >= minY && mouseX <= minX + BOOK_COVER.width && mouseY <= minY + BOOK_COVER.height) {
                    if (isOpening) {// So you can double-click to open it instantly
                        isOpen = true;
                    }
                    isOpening = true;
                }
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
