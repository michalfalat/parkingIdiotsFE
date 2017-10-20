import { PlatformV1Page } from './app.po';

describe('platform-v1 App', () => {
  let page: PlatformV1Page;

  beforeEach(() => {
    page = new PlatformV1Page();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!!');
  });
});
